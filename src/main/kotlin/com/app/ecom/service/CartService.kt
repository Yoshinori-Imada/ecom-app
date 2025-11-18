package com.app.ecom.service

import com.app.ecom.dto.CartItemRequestDto
import com.app.ecom.model.CartItem
import com.app.ecom.repositories.CartItemRepository
import com.app.ecom.repositories.ProductRepository
import com.app.ecom.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class CartService(
    private val cartItemRepository: CartItemRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun addProductToCart(userId: Long, request: CartItemRequestDto): Boolean {
        val product = productRepository.findByIdOrNull(request.productId)
            ?: return false

        val user = userRepository.findByIdOrNull(userId)
            ?: return false

        if (product.stockQuantity < request.quantity) {
            return false
        }

        val existingCartItem = cartItemRepository.findByUserAndProduct(user, product)

        if (existingCartItem != null) {
            val newQuantity = existingCartItem.quantity + request.quantity

            if (product.stockQuantity < newQuantity) {
                return false
            }

            val updateItem = existingCartItem.copy(
                quantity = newQuantity,
                price = product.price.multiply(BigDecimal(newQuantity))
            )
            cartItemRepository.save(updateItem)
        } else {
            val newItem = CartItem(
                user = user,
                product = product,
                quantity = request.quantity,
                price = product.price.multiply(BigDecimal(request.quantity))
            )
            cartItemRepository.save(newItem)
        }
        return true
    }

    // --- ユーザーのカートの内容を取得するメソッド ---
    fun getCartItems(userId: Long): List<CartItem> {
        val user = userRepository.findByIdOrNull(userId)
        return user?.let { cartItemRepository.findByUserWithProducts(it) } ?: emptyList()
    }

    @Transactional
    fun clearCart(userId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: return
        cartItemRepository.deleteByUser(user)
    }
}