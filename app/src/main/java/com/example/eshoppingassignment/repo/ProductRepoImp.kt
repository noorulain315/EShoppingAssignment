package com.example.eshoppingassignment.repo

import androidx.annotation.VisibleForTesting
import com.example.eshoppingassignment.data.apigateway.ProductGateway
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.data.models.AddProductResponse
import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.data.models.ProductResponseItem
import com.example.eshoppingassignment.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ProductRepoImp @Inject constructor(
    private val productGateway: ProductGateway,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepo {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    suspend fun <T> performApiCall(api: suspend () -> Response<T>): Resource<T> {
        return withContext(dispatcher) {
            try {
                val response = api()
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Resource.ResourceSuccess(result)
                } else {
                    Resource.ResourceError(Exception(response.message()))
                }
            } catch (e: Exception) {
                Resource.ResourceError(Exception("An error occurred"))
            }
        }
    }

    override suspend fun getProducts(): Resource<ProductResponse> {
        return performApiCall { productGateway.getProducts() }
    }

    override suspend fun deleteProducts(productId: Int): Resource<ProductResponseItem> {
        return performApiCall { productGateway.deleteProduct(productId) }
    }

    override suspend fun addProducts(productRequest: AddProductRequest): Resource<AddProductResponse> {
        return performApiCall { productGateway.addProduct(productRequest) }
    }
}