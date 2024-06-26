package com.example.equipodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.equipodos.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    //registerUser se comunica con el repository
    fun registerUser(email: String, pass: String, nombre: String, apellido: String, isRegister: (Boolean) -> Unit) {
        repository.registerUser(email, pass, nombre, apellido) { response ->
            isRegister(response)
        }
    }
    //loginUser se comunica con el repository
    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
        repository.loginUser(email, pass){ response ->
            isLogin(response)
        }
    }

    fun obtenerNombreApellidoUsuario(email: String, onComplete: (nombre: String?, apellido: String?) -> Unit) {
        repository.obtenerNombreApellidoUsuario(email) { nombre, apellido ->
            onComplete(nombre, apellido)
        }
    }

    // Método para obtener el usuario actual
    fun getCurrentUser(): FirebaseUser? {
        return repository.getCurrentUser()
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}