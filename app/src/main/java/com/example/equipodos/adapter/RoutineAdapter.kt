package com.example.equipodos.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.databinding.ItemRoutineBinding
import com.example.equipodos.model.Routine

class RoutineAdapter : ListAdapter<Routine, RoutineAdapter.RoutineViewHolder>(RoutineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = ItemRoutineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val currentRoutine = getItem(position)
        holder.bind(currentRoutine)
    }

    /*inner class RoutineViewHolder(private val binding: ItemRoutineBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(routine: Routine) {
            binding.apply {
                textViewName.text = routine.routineName
                textViewExercise.text = "Ejercicios: ${routine.exercises.size}"
                root.setOnClickListener {
                    val action = RoutineFragmentDirections.actionRoutineFragmentToDetailRoutineFragment3(routine.id)
                    it.findNavController().navigate(action)
                }
            }
        }
    }*/
    inner class RoutineViewHolder(private val binding: ItemRoutineBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(routine: Routine) {
            binding.apply {
                textViewName.text = routine.routineName
                textViewExercise.text = "Ejercicios: ${routine.exercises.size}"
                root.setOnClickListener {
                    // Crear un Bundle y poner los datos en él
                    val bundle = Bundle().apply {
                        putString("routineId", routine.id)
                        putString("routineName", routine.routineName)
                        putSerializable("exercises", ArrayList(routine.exercises))
                    }
                    // Navegar al DetailRoutineFragment con el Bundle como argumentos
                    it.findNavController().navigate(R.id.action_routineFragment_to_detailRoutineFragment3, bundle)
                }
            }
        }
    }


    class RoutineDiffCallback : DiffUtil.ItemCallback<Routine>() {
        override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem.routineName == newItem.routineName
        }

        override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
            return oldItem == newItem
        }
    }
}

