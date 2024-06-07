package com.example.equipodos.view.fragment


import com.example.equipodos.view.ExerciseAdapter
import RoutineViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.model.Exercise
import com.example.equipodos.model.Routine

import com.google.firebase.auth.FirebaseAuth



class CreateRutineFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RoutineViewModel
    private val exercises: MutableList<Exercise> = mutableListOf()
    private lateinit var exerciseAdapter: ExerciseAdapter // Agrega una propiedad para el adaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_rutine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        auth = FirebaseAuth.getInstance()

        val toolbar = view.findViewById<Toolbar>(R.id.create_toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val exercisesRecyclerView = view.findViewById<RecyclerView>(R.id.Exercises)
        exerciseAdapter = ExerciseAdapter(exercises) // Inicializa el adaptador
        exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        exercisesRecyclerView.adapter = exerciseAdapter // Asigna el adaptador al RecyclerView

        val usuario = auth.currentUser
        val email = usuario?.email.toString()
        val nameRutina = view.findViewById<EditText>(R.id.nombreRutina)

        val addExerciseButton = view.findViewById<ImageButton>(R.id.nuevoEjercicio)
        addExerciseButton.setOnClickListener {
            // Agregar un nuevo ejercicio a la lista
            val newExercise = Exercise("Nuevo ejercicio", 0, 0)
            exercises.add(newExercise)
            exerciseAdapter.notifyItemInserted(exercises.size - 1) // Notificar al adaptador sobre el nuevo elemento
        }

        val buttonRegistrar = view.findViewById<Button>(R.id.crearRutina)
        buttonRegistrar.setOnClickListener {
            val nombreRutina = nameRutina.text.toString()
            if (exercises.isNotEmpty()) {
                viewModel.registrarRutina(email, nombreRutina, exercises)
            } else {
                Toast.makeText(context, "Agregue al menos un ejercicio.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.exitoRegistro.observe(viewLifecycleOwner, Observer { exito ->
            if (exito) {
                Toast.makeText(context, "Rutina registrada exitosamente", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Error al registrar la rutina", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

