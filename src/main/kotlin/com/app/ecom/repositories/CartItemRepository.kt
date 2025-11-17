package com.app.ecom.repositories

import com.app.ecom.model.CartItem
import com.app.ecom.model.Product
import com.app.ecom.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    fun findByUserAndProduct(
        user: User,
        product: Product
    ): CartItem?

}