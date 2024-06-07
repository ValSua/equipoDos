package com.example.equipodos.model

import com.google.gson.annotations.SerializedName


data class Exercises (
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String)


