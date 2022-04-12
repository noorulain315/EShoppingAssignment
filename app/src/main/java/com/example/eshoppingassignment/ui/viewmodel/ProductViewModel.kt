package com.example.eshoppingassignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.data.models.ProductResponseItem
import com.example.eshoppingassignment.repo.ProductRepo
import com.example.eshoppingassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepo: ProductRepo
) : ViewModel() {
    private var productResponseLiveData = MutableLiveData<Resource<ProductResponse>>()
    fun getProductResponseLiveData(): LiveData<Resource<ProductResponse>> =
        productResponseLiveData

    private var productDeleteLiveData = MutableLiveData<Resource<ProductResponseItem>>()
    fun getProductDeleteLiveData(): LiveData<Resource<ProductResponseItem>> =
        productDeleteLiveData

    fun getProducts() {
        viewModelScope.launch {
            productResponseLiveData.postValue(Resource.ResourceLoading())
            productResponseLiveData.postValue(productRepo.getProducts())
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            productDeleteLiveData.postValue(Resource.ResourceLoading())
            productDeleteLiveData.postValue(productRepo.deleteProducts(productId))
        }
    }

}