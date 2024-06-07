package net.babanin.rabbitmq.producer

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SingleController(val messageService: MessageService, val messageGeneratorService: MessageGeneratorService) {
    @PostMapping(value = ["/customers/{customerId}"])
    fun makeCustomerAction(@PathVariable customerId: String, @RequestBody action: CustomerAction) {
        messageService.sendMessage(customerId, action)
    }

    @PostMapping(value = ["/reset-total-count"])
    fun resetTotalCount() {
        messageGeneratorService.resetTotalCount()
    }
}