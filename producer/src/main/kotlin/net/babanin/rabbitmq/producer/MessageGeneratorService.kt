package net.babanin.rabbitmq.producer

import com.fasterxml.jackson.databind.ObjectMapper
import net.babanin.rabbitmq.producer.MessageService.Companion.CUSTOMER_TOPIC
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageGeneratorService(
    val producerConfig: ProducerConfig,
    val rabbitTemplate: RabbitTemplate,
    val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(MessageGeneratorService::class.java)

    private var clientState: IntArray = IntArray(producerConfig.maxClientCount)

    @Volatile
    private var totalCount = 0

    @Scheduled(initialDelay = 10_000, fixedDelayString = "\${producer.delay-between-messages}")
    fun sendMessage() {
        if (producerConfig.maxMessageCount != -1 && totalCount == producerConfig.maxMessageCount) {
            return
        }

        totalCount++

        val rnd = Random()
        val nextClient = rnd.nextInt(producerConfig.maxClientCount)
        val clientValue = ++clientState[nextClient]

        val messageProperties = MessageProperties()
        messageProperties.headers["x-customer-id"] = nextClient

        val message = Message(objectMapper.writeValueAsBytes(ClientValue(nextClient, clientValue)), messageProperties)

        rabbitTemplate.send(CUSTOMER_TOPIC, nextClient.toString(), message)
    }
    
    fun resetTotalCount() {
        totalCount = 0
    }

    data class ClientValue(val clientId: Int, val clientValue: Int)
}