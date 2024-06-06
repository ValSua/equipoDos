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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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



        // Inicializar el RecyclerView y el adaptador con una lista vacía
        var exercisesRecyclerView = view.findViewById<RecyclerView>(R.id.Exercises)
        var exerciseAdapter = ExerciseAdapter(exercises) // Lista vacía
        exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        exercisesRecyclerView.adapter = exerciseAdapter



        val usuario = auth.currentUser // Obtén el email del usuario autenticado
        val email = usuario?.email.toString()
        val nameRutina = view.findViewById<EditText>(R.id.nombreRutina)

        // Datos de ejemplo
        val ejercicio1 = Exercise("Sentadilla", 10, 8)
        val ejercicio2 = Exercise("Saltos Verticales", 15, 4)
        val ejercicios = mutableListOf(ejercicio1, ejercicio2)
        val nuevaRutina = Routine(nameRutina.text.toString(), exercises)

        // Botón para registrar la rutina
        val buttonRegistrar = view.findViewById<Button>(R.id.crearRutina)
        buttonRegistrar.setOnClickListener {
            viewModel.registrarRutina(email, nuevaRutina)
        }

        val addExerciseButton = view.findViewById<ImageButton>(R.id.nuevoEjercicio)
        addExerciseButton.setOnClickListener {
            // Agregar un nuevo ejercicio a la lista
            val newExercise = ejercicio1
            exercises.add(newExercise)
            // Notificar al adaptador de los cambios en los datos
            exerciseAdapter.notifyDataSetChanged()
        }

        viewModel.exitoRegistro.observe(viewLifecycleOwner, Observer { exito ->
            if (exito) {
                Toast.makeText(context, "Rutina registrada exitosamente", Toast.LENGTH_SHORT).show()
                // Aquí puedes actualizar la UI o navegar a otro fragmento
            } else {
                Toast.makeText(context, "Error al registrar la rutina", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostraremail(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.email.toString()
        Toast.makeText(requireContext(), userId, Toast.LENGTH_SHORT).show()
    }

}
