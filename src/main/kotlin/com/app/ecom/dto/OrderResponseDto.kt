package com.app.ecom.dto

import com.app.ecom.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

// 注文全体を返すDTO
data class OrderResponseDto(
    val id: Long?,
    val userId: Long,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val items: List<OrderItemDto>,
    val createdAt: LocalDateTime?
)
