package com.example.equipodos.view.viewholder

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.databinding.ItemDetailsExerciseBinding
import com.example.equipodos.model.ListExercise

class ExerciseViewHolder (binding: ItemDetailsExerciseBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
        val bindingItem = binding
        val navController = navController
    fun setItemDetailExercise(exercise: ListExercise) {
        bindingItem.namExercise.text = exercise.name
        bindingItem.tvDescription.text = exercise.description
        
        if (exercise.isSelected) {
            bindingItem.ivCheck.setImageResource(R.drawable.fa__check_circle)
        } else {
            bindingItem.ivCheck.setImageResource(R.drawable.fa__check_circle)
        }

      
        bindingItem.ivCheck.setOnClickListener {
            
            exercise.isSelected = !exercise.isSelected

            
            if (exercise.isSelected) {
                bindingItem.ivCheck.setImageResource(R.drawable.fa__check_circle)
            } else {
                bindingItem.ivCheck.setImageResource(R.drawable.fa__check_circle)
            }

          }
    }

}