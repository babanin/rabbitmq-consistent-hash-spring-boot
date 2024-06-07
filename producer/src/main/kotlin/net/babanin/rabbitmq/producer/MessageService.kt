package net.babanin.rabbitmq.producer

import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.nio.charset.Charset

@Service
class MessageService(val rabbitTemplate: RabbitTemplate) {

    fun sendMessage(customerId: String, action: CustomerAction) {
        val messageProperties = MessageProperties()
        messageProperties.headers["x-customer-id"] = customerId
        
        val message = Message(action.action.toByteArray(Charset.defaultCharset()), messageProperties)

        rabbitTemplate.send(CUSTOMER_TOPIC, "customer-action", message)
    }

    companion object {
        const val CUSTOMER_TOPIC = "customer.topic"
    }
}