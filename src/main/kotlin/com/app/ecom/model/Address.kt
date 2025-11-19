package com.app.ecom.model

import jakarta.persistence.*

@Entity
@Table(name = "address_table")
data class Address(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val zipCode: String? = null
)