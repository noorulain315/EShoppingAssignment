package com.example.eshoppingassignment.data.models

data class AddProductRequest(
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String
)