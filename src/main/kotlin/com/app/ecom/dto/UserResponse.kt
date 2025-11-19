package com.app.ecom.dto

import com.app.ecom.model.UserRole

// サーバーからクライアントに返すデータ
data class UserResponseDto(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phone: String?,
    val role: UserRole,
    val address: AddressDto?
 )