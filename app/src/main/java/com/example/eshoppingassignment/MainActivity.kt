package com.example.eshoppingassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.repo.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getProducts()
        viewModel.deleteProduct(6)
        viewModel.addProduct(AddProductRequest("electronic", "hgasgh","jhgaf", 20.0,"jhfas"))
    }
}