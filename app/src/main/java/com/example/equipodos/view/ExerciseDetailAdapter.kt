package com.example.equipodos.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.model.Exercise

class ExerciseDetailAdapter(private val exercises: MutableList<Exercise>) : RecyclerView.Adapter<ExerciseDetailAdapter.ExerciseViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_exercise, parent, false)
            return ExerciseViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
            val currentExercise = exercises[position]
            holder.exerciseTitle.text = currentExercise.name
            holder.setsInput.setText(currentExercise.sets.toString())
            holder.repsInput.setText(currentExercise.reps.toString())

        }
        override fun getItemCount() = exercises.size

        inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val exerciseTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
            val repsInput: TextView = itemView.findViewById(R.id.repsInput)
            val setsInput: TextView = itemView.findViewById(R.id.setsInput)
            val checkbox: CheckBox = itemView.findViewById(R.id.ejercicioTerminado)

        }


}