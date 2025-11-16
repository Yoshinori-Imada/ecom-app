package com.app.ecom.dto

data class AddressDto(
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val zipCode: String? = null
)
