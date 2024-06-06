package com.example.equipodos.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.model.Exercise

class ExerciseAdapter(private val exercises: MutableList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = exercises[position]
        holder.exerciseTitle.text = currentExercise.name
        holder.repsInput.setText(currentExercise.reps.toString())
        holder.setsInput.setText(currentExercise.sets.toString())

        holder.deleteButton.setOnClickListener {
            exercises.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = exercises.size

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
        val repsInput: EditText = itemView.findViewById(R.id.repsInput)
        val setsInput: EditText = itemView.findViewById(R.id.setsInput)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }
}
