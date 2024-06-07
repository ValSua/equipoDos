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

    private val checkedMap = mutableMapOf<Int, Boolean>() // Mapa para mantener el estado de las casillas de verificación

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = exercises[position]
        holder.bind(currentExercise, checkedMap[position] ?: false) // Bind de los datos y estado de la casilla de verificación
    }

    override fun getItemCount() = exercises.size

    fun areAllChecked(): Boolean {
        if (checkedMap.isEmpty()) {
            return false // Si no hay ninguna casilla de verificación, retornar false
        }
        return checkedMap.values.all { it } // Verificar si todas las casillas de verificación están marcadas
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
        private val repsInput: TextView = itemView.findViewById(R.id.repsInput)
        private val setsInput: TextView = itemView.findViewById(R.id.setsInput)
        private val checkbox: CheckBox = itemView.findViewById(R.id.ejercicioTerminado)

        init {
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                checkedMap[adapterPosition] = isChecked // Actualizar el estado de la casilla de verificación en el mapa
                notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
            }
        }

        fun bind(exercise: Exercise, isChecked: Boolean) {
            exerciseTitle.text = exercise.name
            repsInput.text = exercise.reps.toString()
            setsInput.text = exercise.sets.toString()
            checkbox.isChecked = isChecked

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                checkedMap[adapterPosition] = isChecked // Actualizar el estado de la casilla de verificación en el mapa
                notifyAllCheckedStatusChanged()
            }
        }

        fun notifyAllCheckedStatusChanged() {
            if (areAllChecked()) {
                // Realizar acciones si todos los elementos están marcados
            } else {
                // Realizar acciones si al menos un elemento no está marcado
            }
        }
    }
}

