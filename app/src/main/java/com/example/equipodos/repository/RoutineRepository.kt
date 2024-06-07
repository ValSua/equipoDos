package com.example.equipodos.repository

import com.example.equipodos.model.Exercise
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
                val rutinas = document.get("rutinas") as? MutableList<Map<String, Any>> ?: mutableListOf()

                // Crear una lista de ejercicios para la nueva rutina
                val nuevosEjercicios = rutina.exercise.map { ejercicio ->
                    // Crear un nuevo mapa para cada ejercicio con sus valores correspondientes
                    mapOf(
                        "name" to ejercicio.name,
                        "reps" to ejercicio.reps,
                        "sets" to ejercicio.sets
                    )
                }

                // Crear un nuevo mapa para la nueva rutina con su lista de ejercicios
                val nuevaRutinaMap = mapOf(
                    "nombre" to rutina.nombre,
                    "exercise" to nuevosEjercicios
                )

                // Agregar la nueva rutina a la lista de rutinas
                rutinas.add(nuevaRutinaMap)

                // Actualizar la lista de rutinas en la base de datos
                usuarioRef.update("rutinas", rutinas).addOnCompleteListener { task ->
                    callback(task.isSuccessful)
                }
            } else {
                callback(false) // El documento no existe
            }
        }.addOnFailureListener {
            callback(false) // Error al obtener el documento
        }
    }



    fun obtenerRutina(email: String, index: Int, callback: (Routine?) -> Unit) {
        val usuarioRef = db.collection("usuarios").document(email)
        usuarioRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val rutinas = document.get("rutinas") as? List<Map<String, Any>>
                if (rutinas != null && index in 0 until rutinas.size) {
                    val rutina = rutinas[index]
                    val nombre = rutina["nombre"] as? String ?: ""
                    val ejercicios = (rutina["exercise"] as? List<Map<String, Any>>)?.map { ejercicio ->
                        Exercise(
                            ejercicio["name"] as? String ?: "",
                            (ejercicio["sets"] as? Long ?: 0).toInt(),
                            (ejercicio["reps"] as? Long ?: 0).toInt()

                        )
                    } ?: emptyList()
                    callback(Routine(nombre, ejercicios))
                } else {
                    callback(null) // Índice fuera de rango o rutinas es null
                }
            } else {
                callback(null) // El documento no existe
            }
        }.addOnFailureListener {
            callback(null) // Error al obtener el documento
        }
    }





    fun actualizarRutina(email: String, index: Int, newExercises: List<Exercise>, callback: (Boolean) -> Unit) {
        val usuarioRef = db.collection("usuarios").document(email)
        usuarioRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val rutinas = document.get("rutinas") as? MutableList<Map<String, Any>> ?: mutableListOf()
                if (index in 0 until rutinas.size) {
                    val rutina = rutinas[index].toMutableMap()
                    rutina["exercise"] = newExercises.map { exercise ->
                        mapOf(
                            "name" to exercise.name,
                            "sets" to exercise.sets,
                            "reps" to exercise.reps
                        )
                    }
                    rutinas[index] = rutina
                    usuarioRef.update("rutinas", rutinas).addOnCompleteListener { task ->
                        callback(task.isSuccessful)
                    }
                } else {
                    callback(false) // El índice está fuera de rango
                }
            } else {
                callback(false) // El documento no existe
            }
        }.addOnFailureListener {
            callback(false) // Error al obtener el documento
        }
    }

}
