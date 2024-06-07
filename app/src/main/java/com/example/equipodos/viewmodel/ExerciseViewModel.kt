package com.example.equipodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipodos.model.Exercises
import com.example.equipodos.model.ListExercise
import com.example.equipodos.repository.ExerciseRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel  @Inject constructor(
    private val repository : ExerciseRepository ): ViewModel() {

    private val _listExercise = MutableLiveData<MutableList<ListExercise>>()
    val listExercise: LiveData<MutableList<ListExercise>> get() = _listExercise

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    private val _listExercises = MutableLiveData<MutableList<Exercises>>()
    val listExercises: LiveData<MutableList<Exercises>> = _listExercises

    fun guardarEjercicio(listExercise: ListExercise) {
        viewModelScope.launch {

            _progresState.value = true
            try {
                repository.guardarEjercicio(listExercise)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getExercise() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listExercises.value = repository.getExercise()
                _progresState.value = false

            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    suspend fun getListExercises(): MutableList<ListExercise> {
        return withContext(Dispatchers.IO){
            val exerciseCollection = FirebaseFirestore.getInstance().collection("ListExercise")
            val exerciseSnapshot = exerciseCollection.get().await()
            val exerciseList = mutableListOf<ListExercise>()
            for (document in exerciseSnapshot.documents) {
                val exercise = document.toObject<ListExercise>()
                exercise?.let {
                    exerciseList.add(it)
                }
            }
            exerciseList
            }
    }
}