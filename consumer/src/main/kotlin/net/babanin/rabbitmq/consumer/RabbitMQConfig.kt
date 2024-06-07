package net.babanin.rabbitmq.consumer

import org.springframework.amqp.core.*
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@EnableRabbit
class RabbitMQConfig {
    val CUSTOMER_QUEUE = "customer-queue-" + UUID.randomUUID().toString()

    @Bean
    fun customerQueue(): Queue = Queue(
        CUSTOMER_QUEUE,
        false, false, true
    )

    @Bean
    fun customExchange(): Exchange {
        return CustomExchange(
            "customer.topic", "x-consistent-hash", true, false,
            mapOf<String, String>("hash-header" to "x-customer-id")
        )
    }

    @Bean
    fun messageConverter() = Jackson2JsonMessageConverter()
    
    @Bean
    fun customerBinding(customerQueue: Queue, customExchange: Exchange): Binding =
        BindingBuilder
            .bind(customerQueue)
            .to(customExchange)
            .with("1")
            .noargs()
}