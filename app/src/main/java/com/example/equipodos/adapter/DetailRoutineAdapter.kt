package com.example.equipodos.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.databinding.ItemDetailExerciseBinding
import com.example.equipodos.model.Exercise

class DetailRoutineAdapter : ListAdapter<Exercise, DetailRoutineAdapter.DetailRoutineViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRoutineViewHolder {
        val binding = ItemDetailExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailRoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailRoutineViewHolder, position: Int) {
        val exercise = getItem(position)
        Log.d("DetailRoutineAdapter", "Binding exercise to view: $exercise")
        holder.bind(exercise)
    }


    class DetailRoutineViewHolder(private val binding: ItemDetailExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.textViewNameExercise.text = exercise.name
            binding.editTextSets.setText(exercise.sets.toString())
            binding.editTextReps.setText(exercise.reps.toString())

            binding.radioButton.setOnClickListener {
                Toast.makeText(binding.root.context, "Haz empezado tu ejercicio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            val areItemsTheSame = oldItem.name == newItem.name
            Log.d("ExerciseDiffCallback", "Comparing items: $oldItem vs. $newItem. Are items the same? $areItemsTheSame")
            return areItemsTheSame
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            val areContentsTheSame = oldItem == newItem
            Log.d("ExerciseDiffCallback", "Comparing contents: $oldItem vs. $newItem. Are contents the same? $areContentsTheSame")
            return areContentsTheSame
        }
    }

}

