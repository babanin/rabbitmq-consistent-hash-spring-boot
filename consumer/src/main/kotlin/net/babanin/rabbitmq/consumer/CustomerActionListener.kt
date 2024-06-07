package net.babanin.rabbitmq.consumer

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class CustomerActionListener {
    val logger = LoggerFactory.getLogger(javaClass)
    
    @RabbitListener(queues = ["#{customerQueue.name}"])
    fun listener(@Payload action: String) {
        logger.info("Received customer action: {}", action)
    }
}
