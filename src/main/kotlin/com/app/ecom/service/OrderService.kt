package com.app.ecom.service

import com.app.ecom.dto.OrderItemDto
import com.app.ecom.dto.OrderResponseDto
import com.app.ecom.model.Order
import com.app.ecom.model.OrderItem
import com.app.ecom.model.OrderStatus
import com.app.ecom.repositories.OrderRepository
import com.app.ecom.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val cartService: CartService // カートの中身を取得・クリアするために注入
) {

    @Transactional
    fun createOrder(userId: Long): OrderResponseDto? {
        // 1. ユーザーの存在確認
        val user = userRepository.findByIdOrNull(userId) ?: return null

        // 2. カートの中身を取得
        val cartItems = cartService.getCartItems(userId)
        if (cartItems.isEmpty()) return null

        // 3. 合計金額の計算
       // val totalAmount = cartItems.sumOf { it.price }
        val totalAmount = cartItems.sumOf { it.price * it.quantity.toBigDecimal() }

        // 4. 注文エンティティの作成
        val order = Order(
            user = user,
            totalAmount = totalAmount,
            status = OrderStatus.CONFIRMED,
            items = ArrayList()
        )

        // 5. OrderItemリストを作成
        val orderItems = cartItems.map { cartItem ->
            OrderItem(
                order = order,
                product = cartItem.product,
                quantity = cartItem.quantity,
                price = cartItem.price,
            )
        }

        // 6. （親：Order）エンティティに（子：OrderItem）リストを追加
        (order.items as MutableList).addAll(orderItems)

        // 7. 注文の保存
        val savedOrder = orderRepository.save(order) // CascadeType.ALLによりOrderItemも保存される

        // 8. カートのクリア (CartServiceにメソッド追加が必要)
        cartService.clearCart(userId)

        // 9. レスポンスDTOへの変換
        return savedOrder.toResponseDto()
    }

    // --- DTO変換ロジック ---
    private fun Order.toResponseDto(): OrderResponseDto {
        val itemDtos = this.items.map {
            OrderItemDto(
                id = it.id,
                productId = it.product.id!!,
                quantity = it.quantity,
                price = it.price,
                subTotal = it.subTotal
            )
        }
        return OrderResponseDto(
            id = this.id,
            userId = this.user.id!!,
            totalAmount = this.totalAmount,
            status = this.status,
            items = itemDtos,
            createdAt = this.createdAt
        )
    }
}