package com.example.equipodos.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
            // Handle routine button click, if needed
        }
    }

    override fun getItemCount() = routines.size

    inner class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineButton: Button = itemView.findViewById(R.id.nameRoutine)
    }
}
