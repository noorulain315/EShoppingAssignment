package com.example.eshoppingassignment.viewmodels

import com.example.eshoppingassignment.base.BaseViewModelTest
import com.example.eshoppingassignment.data.models.*
import com.example.eshoppingassignment.ui.viewmodel.AddProductViewModel
import com.example.eshoppingassignment.repo.ProductRepo
import com.example.eshoppingassignment.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddProductViewModelTest : BaseViewModelTest() {
    @InjectMockKs
    lateinit var viewModel: AddProductViewModel

    @MockK
    lateinit var productRepo: ProductRepo

    private val liveAddProductResponse = mutableListOf<Resource<AddProductResponse>>()

    @Before
    fun setup() {
        viewModel.getAddProductLiveDataLiveData().observeForever {
            liveAddProductResponse.add(it)
        }
    }

    @Test
    fun `test delete product response success`() {
        coEvery { productRepo.addProducts(any()) }.returns(
            Resource.ResourceSuccess(AddProductResponse(1))
        )
        viewModel.addProduct(AddProductRequest("", "", "", 2.2, ""))
        assert(liveAddProductResponse[0] is Resource.ResourceLoading)
        assert(liveAddProductResponse[1] is Resource.ResourceSuccess)
    }

    @Test
    fun `test delete product response failure`() {
        coEvery { productRepo.addProducts(any()) }.returns(
            Resource.ResourceError(Exception(""))
        )
        viewModel.addProduct(AddProductRequest("", "", "", 2.2, ""))
        assert(liveAddProductResponse[0] is Resource.ResourceLoading)
        assert(liveAddProductResponse[1] is Resource.ResourceError)
    }
}