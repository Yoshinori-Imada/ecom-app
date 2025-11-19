package com.app.ecom.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_id_seq", allocationSize = 50)
    val id: Long? = null,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val category: String,
    val imageUrl: String? = null,
    val active: Boolean = true,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null
)