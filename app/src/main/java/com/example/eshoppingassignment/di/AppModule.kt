package com.example.eshoppingassignment.di

import com.example.eshoppingassignment.data.apigateway.ProductGateway
import com.example.eshoppingassignment.repo.ProductRepo
import com.example.eshoppingassignment.repo.ProductRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://fakestoreapi.com/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProductGateway(): ProductGateway = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductGateway::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(gateway: ProductGateway): ProductRepo = ProductRepoImp(gateway)

}