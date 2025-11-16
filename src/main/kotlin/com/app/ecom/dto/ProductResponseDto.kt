package com.app.ecom.dto

import java.math.BigDecimal

data class ProductResponseDto(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val category: String,
    val imageUrl: String? = null,
    val active: Boolean,
    val createdAt: String,
    val updatedAt: String
)
