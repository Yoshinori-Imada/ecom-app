package com.app.ecom.repositories

import com.app.ecom.model.CartItem
import com.app.ecom.model.Product
import com.app.ecom.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    companion object {
        private const val JOIN_FETCH_BY_USER =
            "SELECT ci FROM CartItem ci JOIN FETCH ci.product WHERE ci.user = :user"
    }

    fun findByUserAndProduct(
        user: User,
        product: Product
    ): CartItem?

    fun findByUser(user: User): List<CartItem>

    @Query(JOIN_FETCH_BY_USER)
    fun findByUserWithProducts(user: User): List<CartItem>

    fun deleteByUser(user: User)

}