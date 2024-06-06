package com.example.equipodos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R

class RoutineAdapter(private val routines: MutableList<Pair<String, String>>) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        return RoutineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val currentRoutine = routines[position]
        holder.routineButton.text = currentRoutine.second // second is the name

        holder.routineButton.setOnClickListener {
            val listposition = position // Obtener la posición del elemento en la lista
            val bundle = Bundle().apply {
                putInt("position", listposition) // Pasar la posición como argumento al fragmento de edición
            }
            // Navegar al fragmento de edición y pasar el ID de la rutina como argumento
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_editFragment, bundle)
        }
    }

    override fun getItemCount() = routines.size

    inner class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineButton: Button = itemView.findViewById(R.id.nameRoutine)
    }
}
