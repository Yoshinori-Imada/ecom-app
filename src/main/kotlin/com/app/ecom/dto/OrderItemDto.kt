package com.app.ecom.dto

import java.math.BigDecimal

// 注文詳細（商品ごとの情報）を返すDTO
data class OrderItemDto(
    val id: Long?,
    val productId: Long,
    val quantity: Int,
    val price: BigDecimal,
    val subTotal: BigDecimal
)
