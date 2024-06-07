package com.example.equipodos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipodos.model.Exercise
import com.example.equipodos.repository.RoutineRepository

import kotlinx.coroutines.launch

class DetailRoutineViewModel(private val repository: RoutineRepository) : ViewModel() {

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> get() = _exercises

    fun fetchExercisesForRoutine(routineId: String) {
        viewModelScope.launch {
            _exercises.value = repository.getExercisesForRoutine(routineId)
        }
    }

}
