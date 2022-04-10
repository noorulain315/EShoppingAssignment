package com.example.eshoppingassignment.data.apigateway

import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.data.models.AddProductResponse
import com.example.eshoppingassignment.data.models.ProductResponseItem
import retrofit2.Response
import retrofit2.http.*

interface ProductGateway {
    @GET("/products")
    suspend fun getProducts(): Response<ProductResponse>

    @POST("/products")
    suspend fun addProduct(@Body addProductRequest: AddProductRequest): Response<AddProductResponse>

    @DELETE("/products/{productId}")
    suspend fun deleteProduct(@Path("productId") productId: Int): Response<ProductResponseItem>
}