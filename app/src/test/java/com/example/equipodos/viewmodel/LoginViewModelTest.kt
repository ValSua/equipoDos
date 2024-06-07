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

        //given (qué necesitamos:condiciones previas necesarias para que la prueba se ejecute correctamente)
        val email = "ejemplo@gmail.com"
        val pass = 1234567
        val nombre = "maria"
        val apellido = "marin"
        //val expectedResult =

        //when (Aquí, ejecutas el código o la función que estás evaluando.)
        //val result =

        //Then (lo que tiene que pasar:resultados esperados )
        //assertEquals(expectedResult, result,0.0)
    }
}