package com.example.equipodos.repository

import com.example.equipodos.model.Routine
import com.example.equipodos.model.Usuario
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

// UsuarioRepository.kt
class RoutineRepository {

    private val db = FirebaseFirestore.getInstance()

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

    fun obtenerIDRutinas(email: String, callback: (List<Pair<String, String>>?) -> Unit) {
        val usuarioRef = db.collection("usuarios").document(email)

        usuarioRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val rutinas = document.get("rutinas") as? List<Map<String, Any>>
                if (rutinas != null) {
                    val result = rutinas.map { rutina ->
                        val key = rutina["key"] as? String ?: ""
                        val nombre = rutina["nombre"] as? String ?: ""
                        Pair(key, nombre)
                    }
                    callback(result)
                } else {
                    callback(emptyList())
                }
            } else {
                callback(emptyList())
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

}
