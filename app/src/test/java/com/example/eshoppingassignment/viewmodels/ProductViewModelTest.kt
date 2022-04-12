package com.example.eshoppingassignment.viewmodels

import com.example.eshoppingassignment.base.BaseViewModelTest
import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.data.models.ProductResponseItem
import com.example.eshoppingassignment.data.models.Rating
import com.example.eshoppingassignment.repo.ProductRepo
import com.example.eshoppingassignment.ui.viewmodel.ProductViewModel
import com.example.eshoppingassignment.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductViewModelTest : BaseViewModelTest() {
    @InjectMockKs
    lateinit var viewModel: ProductViewModel

    @MockK
    lateinit var productRepo: ProductRepo


    private val liveDataProductResponse = mutableListOf<Resource<ProductResponse>>()
    private val liveDataProductDelete = mutableListOf<Resource<ProductResponseItem>>()

    @Before
    fun setup() {
        viewModel.getProductResponseLiveData().observeForever {
            liveDataProductResponse.add(it)
        }
        viewModel.getProductDeleteLiveData().observeForever {
            liveDataProductDelete.add(it)
        }
    }

    @Test
    fun `test product response success`() {
        coEvery { productRepo.getProducts() }.returns(
            Resource.ResourceSuccess(ProductResponse())
        )
        viewModel.getProducts()
        assert(liveDataProductResponse[0] is Resource.ResourceLoading)
        assert(liveDataProductResponse[1] is Resource.ResourceSuccess)
    }

    @Test
    fun `test product response failure`() {
        coEvery { productRepo.getProducts() }.returns(
            Resource.ResourceError(Exception(""))
        )
        viewModel.getProducts()
        assert(liveDataProductResponse[0] is Resource.ResourceLoading)
        assert(liveDataProductResponse[1] is Resource.ResourceError)
    }

    @Test
    fun `test delete product response success`() {
        coEvery { productRepo.deleteProducts(any()) }.returns(
            Resource.ResourceSuccess(ProductResponseItem("", "", 1, "", 0.0, Rating(1, 1.0), ""))
        )
        viewModel.deleteProduct(1)
        assert(liveDataProductDelete[0] is Resource.ResourceLoading)
        assert(liveDataProductDelete[1] is Resource.ResourceSuccess)
    }

    @Test
    fun `test delete product response failure`() {
        coEvery { productRepo.deleteProducts(any()) }.returns(
            Resource.ResourceError(Exception(""))
        )
        viewModel.deleteProduct(1)
        assert(liveDataProductDelete[0] is Resource.ResourceLoading)
        assert(liveDataProductDelete[1] is Resource.ResourceError)
    }
}