package com.app.ecom.repositories

import com.app.ecom.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    fun findByUserId(userId: Long): List<Order>

}