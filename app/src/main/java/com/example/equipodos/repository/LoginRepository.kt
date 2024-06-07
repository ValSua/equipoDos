package com.example.equipodos.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()



    fun registerUser(email: String, password: String, nombre: String, apellido: String, isRegisterComplete: (Boolean) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { createUserTask ->
                if (createUserTask.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid
                    val user = hashMapOf(
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "email" to email
                    )
                    email?.let { email ->
                        val userDocRef = db.collection("usuarios").document(email)
                        userDocRef.get().addOnCompleteListener { getUserTask ->
                            if (getUserTask.isSuccessful) {
                                val userDocument = getUserTask.result
                                if (userDocument != null && userDocument.exists()) {
                                    // El usuario ya existe, puede actualizar sus datos si es necesario
                                    isRegisterComplete(true)
                                } else {
                                    // El usuario no existe, agregarlo como nuevo usuario
                                    userDocRef.set(user).addOnCompleteListener { setUserTask ->
                                        isRegisterComplete(setUserTask.isSuccessful)
                                    }
                                }
                            } else {
                                // Error al obtener el documento del usuario
                                isRegisterComplete(false)
                            }
                        }
                    } ?: isRegisterComplete(false)
                } else {
                    // Error al crear el usuario
                    isRegisterComplete(false)
                }
            }
        }
    }



    /*    fun registerUser(email: String, pass: String, isRegisterComplete: (Boolean) -> Unit) {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            isRegisterComplete(true)
                        } else {
                            isRegisterComplete(false)
                        }
                    }
            } else {
                isRegisterComplete(false)
            }
        }*/

    // Método para obtener el usuario actual
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
        } else {
            isLogin(false)
        }
    }
}