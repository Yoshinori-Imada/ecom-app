package com.app.ecom.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users_table")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val phone: String? = null,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.CUSTOMER,

    // one on one relationship
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    // 結合するカラム（外部キー）の名前を"address_id"に指定
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    val address: Address? = null, // Addressオブジェクトを直接持つ

    // タイムスタンプ (Hibernateの機能)
    @CreationTimestamp // 挿入時に自動で現在時刻を設定
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp // 更新時に自動で現在時刻を設定
    val updatedAt: LocalDateTime? = null

)