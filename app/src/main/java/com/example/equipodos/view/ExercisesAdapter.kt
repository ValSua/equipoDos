package com.example.equipodos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.databinding.ItemDetailsExerciseBinding
import com.example.equipodos.model.ListExercise
import com.example.equipodos.view.viewholder.ExerciseViewHolder

class ExercisesAdapter(private val listExercise:MutableList<ListExercise>, private val navController: NavController):
    RecyclerView.Adapter<ExerciseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemDetailsExerciseBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ExerciseViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listExercise.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = listExercise[position]
        holder.setItemDetailExercise(exercise)
    }
}