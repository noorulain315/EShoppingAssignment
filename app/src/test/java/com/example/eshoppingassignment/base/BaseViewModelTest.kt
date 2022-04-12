package com.example.eshoppingassignment.base


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.eshoppingassignment.base.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseViewModelTest : BaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()
}