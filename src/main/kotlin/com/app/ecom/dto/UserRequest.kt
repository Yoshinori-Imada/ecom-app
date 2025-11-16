package com.app.ecom.dto

// クライアントから受け取るデータ
data class UserRequestDto(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val phone: String?,
    val address: AddressDto?
)