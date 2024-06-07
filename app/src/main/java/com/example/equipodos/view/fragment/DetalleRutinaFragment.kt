package com.example.equipodos.view.fragment

import RoutineViewModel
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.databinding.FragmentHomeBinding
import com.example.equipodos.model.Exercise
import com.example.equipodos.view.ExerciseAdapter
import com.example.equipodos.view.ExerciseDetailAdapter
import com.example.equipodos.view.RoutineAdapter
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class DetalleRutinaFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RoutineViewModel
    private val exercises: MutableList<Exercise> = mutableListOf()
    private var routineKey: Int? = null
    val exerciseAdapter = ExerciseDetailAdapter(exercises) // Lista vacía

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_rutina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

        auth = FirebaseAuth.getInstance()



        //colocar condición salir sin terminar
        val toolbar = view.findViewById<Toolbar>(R.id.detail_toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        // Inicializar el RecyclerView y el adaptador con una lista vacía
        val exercisesRecyclerView = view.findViewById<RecyclerView>(R.id.ejercicios)

        exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        exercisesRecyclerView.adapter = exerciseAdapter



        val usuario = auth.currentUser // Obtén el email del usuario autenticado
        val email = usuario?.email.toString()
        val position = requireArguments().getInt("position")
        routineKey = position

        val nombreRutina: TextView = view.findViewById(R.id.nombreRutina)


        // Cargar la rutina desde la base de datos
        routineKey?.let { key ->
            viewModel.obtenerRutina(email, key)
        }





        // Observar la rutina obtenida
        viewModel.routine.observe(viewLifecycleOwner, Observer { rutina ->
            rutina?.let {
                nombreRutina.text = it.nombre
                exercises.clear()
                exercises.addAll(it.exercise)
                exerciseAdapter.notifyDataSetChanged()

            }
        })


        val edittbn = view.findViewById<Button>(R.id.editarRutina)
        edittbn.setOnClickListener {
             // Obtener la posición del elemento en la lista
            val bundle = Bundle().apply {
                putInt("position", position) // Pasar la posición como argumento al fragmento de edición
            }
            // Navegar al fragmento de edición y pasar el ID de la rutina como argumento
            findNavController().navigate(R.id.action_detalleRutinaFragment_to_editFragment,bundle)
        }

        // Verificar si todos los elementos están marcados
        // Verificar si todos los elementos están marcados
        if (exerciseAdapter.areAllChecked()) {
            Toast.makeText(requireContext(), "¡Todos los ejercicios están completados!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Al menos un ejercicio no está completado", Toast.LENGTH_SHORT).show()
        }
        // Mostrar el Toast


    }




}