package com.example.equipodos.repository

import android.util.Log
import com.example.equipodos.model.Exercise
import com.example.equipodos.model.Routine
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*

class RoutineRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveRoutine(routine: Routine) {
        firestore.collection("routines").add(routine).await()
    }

    suspend fun getRoutines(): List<Routine> {
        val querySnapshot: QuerySnapshot = firestore.collection("routines").get().await()
        return querySnapshot.toObjects(Routine::class.java)
    }

    suspend fun getRoutineById(routineId: String): Routine? {
        Log.d("RoutineRepository", "Fetching routine with ID: $routineId")
        val documentSnapshot = firestore.collection("routines").document(routineId).get().await()
        val routine = documentSnapshot.toObject(Routine::class.java)
        routine?.id = documentSnapshot.id
        Log.d("RoutineRepository", "Fetched routine: $routine")
        return routine
    }


    suspend fun getExercisesForRoutine(routineId: String): List<Exercise> {
        val documentSnapshot = firestore.collection("routines").document(routineId).get().await()
        val exercisesMapList = documentSnapshot.get("exercises") as? List<Map<String, Any>> ?: emptyList()
        val exercises = exercisesMapList.map { map ->
            Exercise(
                name = map["name"] as String,
                sets = (map["sets"] as Long).toInt(),
                reps = (map["reps"] as Long).toInt()
            )
        }
        Log.d("RoutineRepository", "Fetched exercises: $exercises")
        return exercises
    }

}