package com.example.eshoppingassignment.base

import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@Suppress("EXPERIMENTAL_API_USAGE")
open class BaseTest {
    private val testScheduler = TestCoroutineScheduler()
    val mainTestThread = UnconfinedTestDispatcher(testScheduler)

    @Before
    open fun initMocks() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        Dispatchers.setMain(mainTestThread)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
}