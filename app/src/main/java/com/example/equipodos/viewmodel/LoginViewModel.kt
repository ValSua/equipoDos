package com.example.equipodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.equipodos.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    //registerUser se comunica con el repository
    fun registerUser(email: String, pass: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, pass) { response ->
            isRegister(response)
        }
    }
    //loginUser se comunica con el repository
    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
        repository.loginUser(email, pass){ response ->
            isLogin(response)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}