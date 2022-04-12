package com.example.eshoppingassignment.data.models

data class Product(
    val id: Int,
    val image: String,
    val title: String,
    var isChecked: Boolean = false
)
