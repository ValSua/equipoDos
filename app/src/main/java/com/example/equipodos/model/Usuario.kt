package com.example.equipodos.model

data class Usuario(
    val email: String,
    val rutinas: List<Routine> = listOf()
)
