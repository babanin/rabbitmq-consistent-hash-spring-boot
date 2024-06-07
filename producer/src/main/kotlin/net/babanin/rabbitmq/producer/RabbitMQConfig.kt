package net.babanin.rabbitmq.producer

import org.springframework.amqp.core.CustomExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
class RabbitMQConfig {

    @Bean
    fun messageConverter() = Jackson2JsonMessageConverter()
    
    @Bean
    fun customExchange() : CustomExchange {
        return CustomExchange("customer.topic", "x-consistent-hash", 
            true, false, mapOf<String, String>("hash-header" to "x-customer-id"))
    }
}