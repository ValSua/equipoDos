package com.example.equipodos.repository

import com.example.equipodos.model.Routine
import com.example.equipodos.model.Usuario
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

// UsuarioRepository.kt
class RoutineRepository {

    private val db = FirebaseFirestore.getInstance()

    fun obtenerUsuario(email: String, callback: (Usuario?) -> Unit) {
        db.collection("usuarios").document(email).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val usuario = document.toObject(Usuario::class.java)
                    callback(usuario)
                } else {
                    callback(null)
                }
            }
    }

    fun registrarUsuario(usuario: Usuario, callback: (Boolean) -> Unit) {
        db.collection("usuarios").document(usuario.email).set(usuario)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun registrarRutina(email: String, rutina: Routine, callback: (Boolean) -> Unit) {
        val usuarioRef = db.collection("usuarios").document(email)

        usuarioRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // El documento existe, actualizar la lista de rutinas
                usuarioRef.update("rutinas", FieldValue.arrayUnion(rutina))
                    .addOnCompleteListener { task ->
                        callback(task.isSuccessful)
                    }
            } else {
                // El documento no existe, crear uno nuevo con la rutina
                val nuevoUsuario = Usuario(email, listOf(rutina))
                usuarioRef.set(nuevoUsuario)
                    .addOnCompleteListener { task ->
                        callback(task.isSuccessful)
                    }
            }
        }.addOnFailureListener {
            callback(false)
        }
    }
}
