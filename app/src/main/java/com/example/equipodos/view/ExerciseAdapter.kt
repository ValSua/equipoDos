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

class ExerciseAdapter(private val exercises: MutableList<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = exercises[position]
        holder.bind(currentExercise)
    }


    override fun getItemCount() = exercises.size

    fun updateExercises(newExercises: List<Exercise>) {
        exercises.clear()
        exercises.addAll(newExercises)
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
        private val repsInput: EditText = itemView.findViewById(R.id.repsInput)
        private val setsInput: EditText = itemView.findViewById(R.id.setsInput)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(exercise: Exercise) {
            // Establecer el nombre del ejercicio
            //exerciseTitle.text = exercise.name

            // Establecer los valores de reps y sets
            repsInput.setText(exercise.reps.toString())
            setsInput.setText(exercise.sets.toString())

            ///////////////////////////////////////
            exerciseTitle.setText(exercise.name.toString())
            ///////////////////////////////////


            // Agregar TextWatcher a los campos de reps y sets
            repsInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val newReps = s?.toString()?.toIntOrNull() ?: 0
                    exercise.reps = newReps
                }
            })

            setsInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val newSets = s?.toString()?.toIntOrNull() ?: 0
                    exercise.sets = newSets
                }
            })

            ////////////////////////////////////////////////////////

            exerciseTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val newName = s?.toString() ?: "Nombre Ejercicio"
                    exercise.name = newName.toString()
                }
            })

            // Agregar clic listener al botón de eliminar
            deleteButton.setOnClickListener {
                // Remover el ejercicio de la lista y notificar al adaptador
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    exercises.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }


    }
}
