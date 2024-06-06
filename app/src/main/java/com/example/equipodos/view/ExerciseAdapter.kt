package com.example.equipodos.view

import android.text.Editable
import android.text.TextWatcher
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
        holder.setsInput.setText(currentExercise.sets.toString())
        holder.repsInput.setText(currentExercise.reps.toString())

        holder.repsInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Actualizar el valor de reps en la lista de ejercicios cuando cambie el texto
                val newReps = s?.toString()?.toIntOrNull() ?: 0
                currentExercise.reps = newReps
            }
        })

        holder.setsInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Actualizar el valor de sets en la lista de ejercicios cuando cambie el texto
                val newSets = s?.toString()?.toIntOrNull() ?: 0
                currentExercise.sets = newSets
            }
        })

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
