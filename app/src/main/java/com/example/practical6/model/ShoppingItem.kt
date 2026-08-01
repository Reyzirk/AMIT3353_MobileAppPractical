package com.example.practical6.model

data class ShoppingItem(
    val id : Int,
    val name: String,
    val category: String,
    val priority: String,
    val urgent: Boolean,
    val purchased: Boolean = false
)
