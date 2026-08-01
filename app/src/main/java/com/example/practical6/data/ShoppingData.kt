package com.example.practical6.data

import com.example.practical6.model.ShoppingItem

object ShoppingData {
    val categories = listOf(
        "Groceries",
        "Household",
        "Electronics",
        "Other"
    )
    val priorities = listOf(
        "Low",
        "Medium",
        "High"
    )
    val sampleItems = listOf(
        ShoppingItem (1, "Milk", "Groceries", "High", urgent = true),
        ShoppingItem (2, "Light Bulbs", "Household", "Low", urgent = false),
        ShoppingItem (3, "USB Cable", "Electronics", "Medium", urgent = false),
        ShoppingItem (4, "Bread", "Groceries", "Medium", urgent = false)
    )
}