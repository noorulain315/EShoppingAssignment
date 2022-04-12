package com.example.eshoppingassignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.data.models.AddProductResponse
import com.example.eshoppingassignment.repo.ProductRepo
import com.example.eshoppingassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepo: ProductRepo,
) : ViewModel() {
    private var addProductLiveData = MutableLiveData<Resource<AddProductResponse>>()
    fun getAddProductLiveDataLiveData(): LiveData<Resource<AddProductResponse>> =
        addProductLiveData

    fun addProduct(productRequest: AddProductRequest) {
        viewModelScope.launch {
            addProductLiveData.postValue(Resource.ResourceLoading())
            addProductLiveData.postValue(productRepo.addProducts(productRequest))
        }
    }
}