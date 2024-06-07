package com.example.equipodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.equipodos.model.Routine
import com.example.equipodos.repository.RoutineRepository
import kotlinx.coroutines.launch

class RoutineViewModel(private val repository: RoutineRepository) : ViewModel() {

    private val _routines = MutableLiveData<List<Routine>>()
    val routines: LiveData<List<Routine>> get() = _routines

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    init {
        loadRoutines()
    }

    private fun loadRoutines() {
        viewModelScope.launch {
            try {
                val routineList = repository.getRoutines()
                _routines.value = routineList
            } catch (e: Exception) {
                _message.value = "Error al cargar las rutinas"
            }
        }
    }
}