package com.app.ecom.repositories

import com.app.ecom.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByActive(active: Boolean): List<Product>
    fun findByIdAndActive(id: Long, active: Boolean): Product?

    @Query(
        "SELECT p FROM Product p WHERE " +
        "p.active = true " +
        "AND p.stockQuantity > 0 " +
        "AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    fun searchProducts(@Param("keyword") keyword: String): List<Product>

}