package net.babanin.rabbitmq.consumer

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerActionListener {
    var totalCount = 0
    var clients = TreeMap<Int, Int>()

    val logger = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["#{customerQueue.name}"])
    fun listener(@Payload action: ClientValue) {
        totalCount++
        clients.compute(action.clientId) { _, v -> if (v == null) 1 else v + 1 }

        logger.info("client_id={} client_value={} // {}", action.clientId, action.clientValue, totalCount)

        if (totalCount % 100 == 0) {
            clients.forEach { (k, v) -> logger.info("\t client_id={} count={}", k, v) }
        }
    }
}
