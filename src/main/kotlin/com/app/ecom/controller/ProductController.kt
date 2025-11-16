package com.app.ecom.controller

import com.app.ecom.dto.ProductRequestDto
import com.app.ecom.dto.ProductResponseDto
import com.app.ecom.model.Product
import com.app.ecom.repositories.ProductRepository
import com.app.ecom.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(
    // Serviceをコンストラクタ注入(DI)
    private val productService: ProductService
) {
    // --- CREATE (POST) ---
    @PostMapping
    fun createProduct(
        @RequestBody request: ProductRequestDto
    ): ResponseEntity<ProductResponseDto> {
        val productResponse = productService.createProduct(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse)
    }

    // --- UPDATE (PUT) ---
    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: ProductRequestDto
    ): ResponseEntity<ProductResponseDto> {

        return productService.updateProduct(id, request)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductResponseDto>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    fun getProductById(
        @PathVariable id: Long
    ): ResponseEntity<ProductResponseDto> {

        return productService.getProductById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        val isSuccess = productService.deleteProduct(id)
        
        return if(isSuccess) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

}