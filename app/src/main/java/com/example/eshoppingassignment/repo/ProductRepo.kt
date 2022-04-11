package com.example.eshoppingassignment.repo

import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.data.models.AddProductResponse
import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.data.models.ProductResponseItem
import com.example.eshoppingassignment.util.Resource

interface ProductRepo {
    suspend fun getProducts(): Resource<ProductResponse>
    suspend fun deleteProducts(productId: Int): Resource<ProductResponseItem>
    suspend fun addProducts(productRequest: AddProductRequest): Resource<AddProductResponse>
}