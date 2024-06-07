package com.example.equipodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipodos.model.Exercise
import com.example.equipodos.model.Routine
import com.example.equipodos.repository.RoutineRepository
import kotlinx.coroutines.launch
import java.util.*


class CreateRoutineViewModel(private val repository: RoutineRepository) : ViewModel() {

    private val _exercises = MutableLiveData<MutableList<Exercise>>(mutableListOf())
    val exercises: LiveData<MutableList<Exercise>> get() = _exercises

    private val _routineName = MutableLiveData<String>()
    val routineName: LiveData<String> get() = _routineName

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    fun addExercise() {
        if ((_exercises.value?.size ?: 0) < 4) {
            _exercises.value?.add(Exercise("", 0, 0))
            _exercises.notifyObserver()
        } else {
            _message.value = "No puedes agregar más ejercicios"
        }
    }

    fun removeExercise(position: Int) {
        _exercises.value?.removeAt(position)
        _exercises.notifyObserver()
    }

    fun saveRoutine() {
        val routine = Routine(id = generateId(), routineName = _routineName.value ?: "", exercises = _exercises.value ?: emptyList())
        viewModelScope.launch {
            try {
                repository.saveRoutine(routine)
                _message.value = "Rutina guardada exitosamente"
            } catch (e: Exception) {
                _message.value = "Error al guardar la rutina"
            }
        }
    }

    private fun generateId(): String {
        return UUID.randomUUID().toString()
    }

    fun setRoutineName(name: String) {
        _routineName.value = name
    }

    fun updateExercise(position: Int, name: String, sets: Int, reps: Int) {
        val updatedList = _exercises.value?.toMutableList()
        updatedList?.get(position)?.apply {
            this.name = name
            this.sets = sets
            this.reps = reps
        }
        _exercises.value = updatedList!!
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    private fun clearRoutineData() {
        _routineName.value = ""
        _exercises.value = mutableListOf()
    }
}