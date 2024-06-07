package com.example.equipodos.model

import java.io.Serializable

data class Exercise(
    var name: String = "",
    var sets: Int = 0,
    var reps: Int = 0
): Serializable