package com.example.equipodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipodos.model.Exercises
import com.example.equipodos.model.ListExercise
import com.example.equipodos.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel  @Inject constructor(
    private val repository : ExerciseRepository ): ViewModel() {

    private val _listExercise = MutableLiveData<MutableList<ListExercise>>()
    val listInventory: LiveData<MutableList<ListExercise>> get() = _listExercise

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    //para almacenar una lista de productos
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
}