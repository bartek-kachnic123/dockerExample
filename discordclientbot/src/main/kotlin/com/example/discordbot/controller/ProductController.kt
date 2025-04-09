package com.example.discordbot.controller

import com.example.discordbot.data.Product

class ProductController {
    // Immutable list of products
    private val products: List<Product> = listOf(
        Product(1, "Laptop", 1),
        Product(2, "TV", 1),
        Product(3, "Book", 2),
        Product(4, "Pencil", 3)
    )

    fun getProductsById(categoryId: Int): List<Product> {
        return products.filter { it.categoryId == categoryId }
    }

    fun getProductsNames(products: List<Product>): List<String> {
        return products.map { it.name }
    }
}