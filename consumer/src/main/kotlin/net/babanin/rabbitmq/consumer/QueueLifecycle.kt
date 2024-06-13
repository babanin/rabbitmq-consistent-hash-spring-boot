package net.babanin.rabbitmq.consumer

import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Binding
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.context.Lifecycle
import org.springframework.stereotype.Component

@Component
class QueueLifecycle(val rabbitAdmin: RabbitAdmin, val customerBinding: Binding) : Lifecycle {
    val logger = LoggerFactory.getLogger(javaClass)

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        logger.warn("Unbind queue from {}", customerBinding)
        rabbitAdmin.removeBinding(customerBinding)
    }


    override fun isRunning(): Boolean = true
}