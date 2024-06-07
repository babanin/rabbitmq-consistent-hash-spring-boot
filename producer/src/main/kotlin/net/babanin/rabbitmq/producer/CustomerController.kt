package net.babanin.rabbitmq.producer

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(val messageService: MessageService) {
    @PostMapping(value = ["/{customerId}"])
    fun makeCustomerAction(@PathVariable customerId: String, @RequestBody action: CustomerAction) {
        messageService.sendMessage(customerId, action)
    }
}