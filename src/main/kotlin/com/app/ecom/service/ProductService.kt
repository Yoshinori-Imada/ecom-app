package com.app.ecom.service

import com.app.ecom.dto.ProductRequestDto
import com.app.ecom.dto.ProductResponseDto
import com.app.ecom.model.Product
import com.app.ecom.repositories.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    // 1. Repositoryをコンストラクタ注入(DI)
    private val productRepository: ProductRepository
) {

    // --- CREATE ---
    @Transactional
    fun createProduct(request: ProductRequestDto): ProductResponseDto {
        // 1. DTOをEntityに変換
        val product = request.toEntity()
        // 2. DBに保存
        val savedProduct = productRepository.save(product)
        // 3. EntityをDTOに変換
        return savedProduct.toResponseDto()
    }

    // --- UPDATE ---
    @Transactional
    fun updateProduct(
        id: Long,
        request: ProductRequestDto
    ): ProductResponseDto? {
        // 1. DBから既存のエンティティを探す
        val existingProduct = productRepository.findById(id).orElse(null)

        // 2. 見つかったら、DTOの内容で変更
        return existingProduct?.let { productToUpdate ->
            // 3. DTOからEntityへ変更内容をマッピング
            val updatedProduct = productToUpdate.copy(
                name = request.name,
                description = request.description,
                price = request.price,
                stockQuantity = request.stockQuantity,
                category = request.category,
                imageUrl = request.imageUrl ?: productToUpdate.imageUrl
            )
            // 4. DBに保存（更新）し、ResponseDTOに変換して返す
            productRepository.save(updatedProduct).toResponseDto()
        }
    }

    // --- GET ---
    @Transactional(readOnly = true)
    fun getAllProducts(): List<ProductResponseDto> {
        return productRepository.findByActive(true)
            .map { it.toResponseDto() }
    }

    @Transactional(readOnly = true)
    fun getProductById(id: Long): ProductResponseDto? {
        return productRepository.findByIdAndActive(id, true)
            ?.toResponseDto()
    }

    // --- DELETE ---
   @Transactional
   fun deleteProduct(id: Long): Boolean {
       val product =productRepository.findByIdOrNull(id)
       return product?.let {
           val deactivatedProduct = it.copy(active=false)
           productRepository.save(deactivatedProduct)
           true
       } ?: false
   }

    // --- SEARCH ---
    fun searchProducts(keyword: String): List<ProductResponseDto> {
        return productRepository.searchProducts(keyword)
            .map { it.toResponseDto() }
    }

    // --- マッパー (Entity <-> DTO 変換ロジック) ---
    private fun ProductRequestDto.toEntity(): Product {
        return Product(
            name = this.name,
            description = this.description,
            price = this.price,
            stockQuantity = this.stockQuantity,
            category = this.category,
            imageUrl = this.imageUrl
        )
    }

    private fun Product.toResponseDto(): ProductResponseDto {
        return ProductResponseDto(
            id = this.id!!,
            name = this.name,
            description = this.description,
            price = this.price,
            stockQuantity = this.stockQuantity,
            category = this.category,
            imageUrl = this.imageUrl,
            active = this.active,
            createdAt = this.createdAt.toString(),
            updatedAt = this.updatedAt.toString()
        )
    }

}
