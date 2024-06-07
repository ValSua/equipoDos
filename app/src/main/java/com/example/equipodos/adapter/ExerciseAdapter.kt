package com.example.equipodos.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.databinding.ItemExerciseBinding
import com.example.equipodos.model.Exercise
import com.example.equipodos.viewmodel.CreateRoutineViewModel


class ExerciseAdapter(
    private val viewModel: CreateRoutineViewModel,
    private val onDeleteClick: (Int) -> Unit
) : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.imageViewClose.setOnClickListener {
            onDeleteClick(position)
        }
    }

    inner class ExerciseViewHolder(val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.editTextNameExercise.setText(exercise.name)
            binding.editTextSets.setText(exercise.sets.toString())
            binding.editTextReps.setText(exercise.reps.toString())

            binding.editTextNameExercise.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel.updateExercise(adapterPosition, s.toString(), exercise.sets, exercise.reps)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            binding.editTextSets.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel.updateExercise(adapterPosition, exercise.name, s.toString().toIntOrNull() ?: 0, exercise.reps)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            binding.editTextReps.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel.updateExercise(adapterPosition, exercise.name, exercise.sets, s.toString().toIntOrNull() ?: 0)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.name == newItem.name && oldItem.sets == newItem.sets && oldItem.reps == newItem.reps
        }
    }
}
