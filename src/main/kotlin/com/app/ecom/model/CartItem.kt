package com.app.ecom.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // --- Relationships（外部キー） ---
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    // --- カート独自のデータ
    val quantity: Int,
    val price: BigDecimal,

    // --- タイムスタンプ ---
    @CreationTimestamp
    val createdAt: java.time.LocalDateTime? = null,
    @CreationTimestamp
    val updatedAt: java.time.LocalDateTime? = null

    )