package com.app.ecom.controller

import com.app.ecom.dto.CartItemRequestDto
import com.app.ecom.model.CartItem
import com.app.ecom.service.CartService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService
) {
    @PostMapping
    fun addProductToCart(
        @RequestHeader("X-User-Id") userId: Long,
        @RequestBody request: CartItemRequestDto
    ): ResponseEntity<String> {
        val isSuccess = cartService.addProductToCart(userId, request)
        return if (isSuccess) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body("Item added to cart successfully")
        } else {
            ResponseEntity
                .badRequest()
                .body("Failed to add item to cart: " +
                    "User not found, Product  not found, or Out of  stock.")
        }
    }

    // --- ユーザーのカート内容を取得するエンドポイント ---
    @GetMapping
    fun getCartItems(
        @RequestHeader("X-User-Id") userId: Long
    ): ResponseEntity<List<CartItem>> {
        val cartItems = cartService.getCartItems(userId)
        return ResponseEntity.ok(cartItems)
    }

}