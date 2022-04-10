package com.example.eshoppingassignment.repo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.util.DispatcherProvider
import com.example.eshoppingassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepo: ProductRepo,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    fun getProducts() {
        viewModelScope.launch(dispatchers.io) {
            when (val response = productRepo.getProducts()) {
                is Resource.ResourceSuccess -> {
                    Log.d("success response", response.toString())
                }
                is Resource.ResourceError -> {
                    Log.d("error response", response.error.localizedMessage)
                }
            }
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch(dispatchers.io) {
            when (val response = productRepo.deleteProducts(productId)) {
                is Resource.ResourceSuccess -> {
                    Log.d("delete success response", response.toString())
                }
                is Resource.ResourceError -> {
                    Log.d("delete error response", response.error.localizedMessage)
                }
            }
        }
    }

    fun addProduct(productRequest: AddProductRequest) {
        viewModelScope.launch(dispatchers.io) {
            when (val response = productRepo.addProducts(productRequest)) {
                is Resource.ResourceSuccess -> {
                    Log.d("add success response", response.toString())
                }
                is Resource.ResourceError -> {
                    Log.d("add error response", response.error.localizedMessage)
                }
            }
        }
    }

}