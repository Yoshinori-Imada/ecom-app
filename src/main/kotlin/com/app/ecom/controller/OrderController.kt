package com.app.ecom.controller

import com.app.ecom.dto.OrderResponseDto
import com.app.ecom.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping
    fun createOrder(
        @RequestHeader("X-User-Id") userId: Long
    ): ResponseEntity<OrderResponseDto> {
        val orderResponse = orderService.createOrder(userId)

        return if (orderResponse != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(orderResponse)
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}