package com.example.equipodos.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.equipodos.repository.RoutineRepository
import com.example.equipodos.viewmodel.CreateRoutineViewModel

class CreateRoutineViewModelFactory(private val repository: RoutineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateRoutineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateRoutineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}