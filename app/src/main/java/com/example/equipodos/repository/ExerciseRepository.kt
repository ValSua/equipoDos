package com.example.equipodos.repository

import com.example.equipodos.model.Exercises
import com.example.equipodos.model.ListExercise
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ExerciseRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun guardarEjercicio(listExercise:ListExercise){
        withContext(Dispatchers.IO){
            val db = FirebaseFirestore.getInstance()
            try{
                db.collection("Exercises").document(listExercise.id.toString()).set(listExercise).await()
            }catch (e: Exception){
                throw e
            }
        }
    }

    fun registrarEjercicio(exercise: Exercises, callback: (Boolean) -> Unit) {
        db.collection("exercises")
            .add(exercise)
            .addOnSuccessListener { documentReference ->
                callback(true)
            }
            .addOnFailureListener { e ->
                callback(false)
            }
    }
}