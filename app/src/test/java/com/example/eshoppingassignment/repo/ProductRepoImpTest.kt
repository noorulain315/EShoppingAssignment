package com.example.eshoppingassignment.repo

import com.example.eshoppingassignment.base.BaseTest
import com.example.eshoppingassignment.data.apigateway.ProductGateway
import com.example.eshoppingassignment.data.models.*
import com.example.eshoppingassignment.util.Resource
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.lang.Exception

@ExperimentalCoroutinesApi
class ProductRepoImpTest : BaseTest() {

    private lateinit var productRepoImp: ProductRepoImp

    @MockK
    lateinit var productGateway: ProductGateway

    @Before
    fun setup() {
        productRepoImp = ProductRepoImp(productGateway, mainTestThread)
    }

    @Test
    fun `test get product response`() = runTest {
        val response = Response.success(ProductResponse())
        coEvery { productGateway.getProducts() }.returns(
            response
        )
        val result = productRepoImp.getProducts()
        assert(result is Resource.ResourceSuccess)

    }

    @Test
    fun `test add product response`() = runTest {
        val response = Response.success(AddProductResponse(1))
        coEvery { productGateway.addProduct(any()) }.returns(
            response
        )
        val result = productRepoImp.addProducts(AddProductRequest("", "", "", 2.2, ""))
        assert(result is Resource.ResourceSuccess)

    }

    @Test
    fun `test delete product response`() = runTest {
        val response = Response.success(ProductResponseItem("", "", 1, "", 0.0, Rating(1, 1.0), ""))
        coEvery { productGateway.deleteProduct(any()) }.returns(
            response
        )
        val result = productRepoImp.deleteProducts(1)
        assert(result is Resource.ResourceSuccess)

    }

    @Test
    fun `test error response`() = runTest {
        val code = "code-1234"
        val error = "error-1234"
        val errorBody =
            "{\"code\" : \"$code\", \"errorCode\" : \"$error\"}".toResponseBody("application/json".toMediaTypeOrNull())
        val response = Response.error<Exception>(404, errorBody)
        val result = productRepoImp.performApiCall { response }
        assert(result is Resource.ResourceError)

    }


}