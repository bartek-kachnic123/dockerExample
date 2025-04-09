package com.example.discordbot.controller

import com.example.discordbot.data.Category

class CategoryController {

    private val categories: List<Category> = listOf(
        Category(1, "Technology"),
        Category(2, "Science"),
        Category(3, "Arts")
    )

    fun getCategoryNames(): List<String> {
        return categories.map { it.name }
    }

    fun getCategoryIdByName(name: String): Int? {
        val category = categories.find { it.name.equals(name, ignoreCase = true) }
        return category?.categoryId
    }
}
