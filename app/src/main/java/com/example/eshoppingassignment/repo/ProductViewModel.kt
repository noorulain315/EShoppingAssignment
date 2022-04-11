package com.example.eshoppingassignment.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.data.models.ProductResponseItem
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

    private var productResponseLiveData = MutableLiveData<Resource<ProductResponse>>()
    fun getProductResponseLiveData(): LiveData<Resource<ProductResponse>> =
        productResponseLiveData

    private var productDeleteLiveData = MutableLiveData<Resource<ProductResponseItem>>()
    fun getProductDeleteLiveData(): LiveData<Resource<ProductResponseItem>> =
        productDeleteLiveData

    fun getProducts() {
        viewModelScope.launch(dispatchers.io) {
            productResponseLiveData.postValue(Resource.ResourceLoading())
            productResponseLiveData.postValue(productRepo.getProducts())
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch(dispatchers.io) {
            productDeleteLiveData.postValue(Resource.ResourceLoading())
            productDeleteLiveData.postValue(productRepo.deleteProducts(productId))
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