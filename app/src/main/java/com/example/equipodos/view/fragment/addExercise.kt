package com.example.equipodos.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.equipodos.databinding.FragmentAddExerciseBinding
import com.example.equipodos.model.ListExercise
import com.example.equipodos.viewmodel.ExerciseViewModel

class addExercise: Fragment() {

    private lateinit var binding: FragmentAddExerciseBinding
    private val exerciseViewModel: ExerciseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExerciseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
    }

    private fun controladores() {
        binding.btnSaveExercisa.setOnClickListener {
            guardarEjercicio()
        }
    }

    private fun guardarEjercicio(){
        val name = binding.etTitle.text.toString()
        val descrip = binding.etDescript.text.toString()
        val exercise = ListExercise(name = name, description = descrip)
        exerciseViewModel.guardarEjercicio(exercise)
        Log.d("test",exercise.toString())
        Toast.makeText(context,"Artículo guardado !!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()

    }
}