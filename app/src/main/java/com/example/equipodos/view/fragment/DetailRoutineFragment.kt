package com.example.equipodos.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipodos.R
import com.example.equipodos.databinding.FragmentDetailRoutineBinding
import com.example.equipodos.model.Exercise
import com.example.equipodos.adapter.DetailRoutineAdapter
import com.example.equipodos.repository.RoutineRepository
import com.example.equipodos.viewmodel.DetailRoutineViewModel
import com.example.equipodos.viewmodel.factory.DetailRoutineViewModelFactory


class DetailRoutineFragment : Fragment() {

    private var _binding: FragmentDetailRoutineBinding? = null
    private val binding get() = _binding!!


    private val viewModel: DetailRoutineViewModel by viewModels {
        DetailRoutineViewModelFactory(RoutineRepository())
    }

    private lateinit var detailRoutineAdapter: DetailRoutineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val routineId = arguments?.getString("routineId")
        val routineName = arguments?.getString("routineName")
        val exercises = arguments?.getSerializable("exercises") as? ArrayList<Exercise>

        Log.d("DetailRoutineFragment", "Received routine ID: $routineId")
        Log.d("DetailRoutineFragment", "Received routine name: $routineName")
        Log.d("DetailRoutineFragment", "Received exercises: $exercises")

        setRecyclerView()

        // Configurar el DetailRoutineAdapter con los ejercicios
        exercises?.let {
            detailRoutineAdapter.submitList(it)
        }

        binding.imageViewBack.setOnClickListener {
            message()
        }
    }


    private fun setRecyclerView() {
        detailRoutineAdapter = DetailRoutineAdapter()
        binding.recyclerViewExercises.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewExercises.adapter = detailRoutineAdapter
    }

    private fun message() {
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setTitle("Advertencia")
            .setMessage("No haz terminado tu rutina. ¿Estas seguro que quieres salir?")
            .setPositiveButton("Sí") { _, _ ->
                findNavController().navigateUp()
            }
            .setNegativeButton("No", null)
            .create()

        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }

        alertDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}