package com.example.equipodos.model

import java.io.Serializable

data class Routine(
    var id: String = "",
    val routineName: String = "",
    val exercises: List<Exercise> = emptyList()
): Serializable




