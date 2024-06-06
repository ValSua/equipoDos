package com.example.equipodos.view.fragment

import RoutineViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.model.Exercise
import com.example.equipodos.model.Routine
import com.example.equipodos.view.ExerciseAdapter
import com.google.firebase.auth.FirebaseAuth

class EditFragment: Fragment()  {

        private lateinit var auth: FirebaseAuth
        private lateinit var viewModel: RoutineViewModel
        private val exercises: MutableList<Exercise> = mutableListOf()
        private var routineKey: Int? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {


            return inflater.inflate(R.layout.fragment_edit_rutine, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            viewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

            auth = FirebaseAuth.getInstance()

            val toolbar = view.findViewById<Toolbar>(R.id.edit_toolbar)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }


            // Inicializar el RecyclerView y el adaptador con una lista vacía
            val exercisesRecyclerView = view.findViewById<RecyclerView>(R.id.Exercises)
            val exerciseAdapter = ExerciseAdapter(exercises) // Lista vacía
            exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            exercisesRecyclerView.adapter = exerciseAdapter



            val usuario = auth.currentUser // Obtén el email del usuario autenticado
            val email = usuario?.email.toString()
            routineKey = 0


            val buttonRegistrar = view.findViewById<Button>(R.id.crearRutina)
//        buttonRegistrar.isEnabled = false
//        buttonRegistrar.isEnabled = exercises.isNotEmpty()

            // Botón para registrar la rutina

            val ejercicio1 = Exercise("Sentadilla", 10, 8)
            val ejercicio2 = Exercise("Saltos Verticales", 15, 4)
            val ejercicios = mutableListOf( ejercicio2)



//            exerciseAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//                override fun onChanged() {
//                    buttonRegistrar.isEnabled = exercises.isNotEmpty()
//                }
//            })

            // Cargar la rutina desde la base de datos
            routineKey?.let { key ->
                viewModel.obtenerRutina(email, key)
            }

            // Observar la rutina obtenida
            viewModel.routine.observe(viewLifecycleOwner, Observer { rutina ->
                rutina?.let {
                    exercises.clear()
                    exercises.addAll(it.exercise)
                    exerciseAdapter.notifyDataSetChanged()
                }
            })


//            boton que debe actualizar la lista en bd
            buttonRegistrar.setOnClickListener {
                viewModel.actualizarRutina(email, 0, exercises)
            }

//            boton para añadir ejercicios a la lista
            val addExerciseButton = view.findViewById<ImageButton>(R.id.nuevoEjercicio)
            addExerciseButton.setOnClickListener {
                // Agregar un nuevo ejercicio a la lista
                val newExercise = ejercicio2
                exercises.add(newExercise)
                // Notificar al adaptador de los cambios en los datos
                exerciseAdapter.notifyDataSetChanged()
            }

            viewModel.updateSuccess.observe(viewLifecycleOwner, Observer { success ->
                if (success) {
                    Toast.makeText(context, "Rutina registrada exitosamente", Toast.LENGTH_SHORT).show()
                    // Regresar al fragmento anterior
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Error al registrar la rutina", Toast.LENGTH_SHORT).show()
                }
            })
        }
}