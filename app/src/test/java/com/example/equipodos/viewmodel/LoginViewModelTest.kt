package com.example.equipodos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.equipodos.repository.LoginRepository
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class LoginViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule() //código que involucra LiveData y ViewModel
    private lateinit var LoginViewModel: LoginViewModel
    private lateinit var LoginRepository: LoginRepository

    @Before
    fun setUp() {
        //LoginViewModel = LoginViewModel(LoginRepository)
        LoginRepository = mock(LoginRepository::class.java)
    }

    @Test
    fun `test método registerUser` (){

    }
}