package com.example.equipodos.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.model.Exercises

class ExercisesAdapter : ListAdapter<Exercises, ExercisesAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_details_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.namExercise)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tvDescription)
        private val checkImageView: ImageView = itemView.findViewById(R.id.ivCheck)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(exercise: Exercises) {
            nameTextView.text = exercise.name
            descriptionTextView.text = exercise.description

            // Aquí puedes configurar las acciones para los botones si es necesario
        }
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercises>() {
        override fun areItemsTheSame(oldItem: Exercises, newItem: Exercises): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Exercises, newItem: Exercises): Boolean {
            return oldItem == newItem
        }
    }
}
