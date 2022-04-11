package com.example.eshoppingassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eshoppingassignment.R
import com.example.eshoppingassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ProductsListFragment.create())
            .commit()
    }
}