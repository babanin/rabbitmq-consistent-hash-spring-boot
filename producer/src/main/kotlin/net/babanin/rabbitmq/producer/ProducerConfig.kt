package net.babanin.rabbitmq.producer

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "producer")
data class ProducerConfig(val maxClientCount: Int, val maxMessageCount : Int, val delayBetweenMessages: Duration) 