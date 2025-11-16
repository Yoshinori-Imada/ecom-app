package com.app.ecom.dto

import java.math.BigDecimal

data class ProductRequestDto(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val category: String,
    val imageUrl: String? = null
)
