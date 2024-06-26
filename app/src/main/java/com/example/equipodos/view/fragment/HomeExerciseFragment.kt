package com.example.equipodos.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.databinding.FragmentHomeExerciseBinding
import com.example.equipodos.view.ExercisesAdapter
import com.example.equipodos.viewmodel.ExerciseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class HomeExerciseFragment : Fragment() {

    private lateinit var binding: FragmentHomeExerciseBinding
    private val exerciseViewModel: ExerciseViewModel by viewModels()

    private lateinit var adapter: ExercisesAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var progress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_home_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.include)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val svBuscar: SearchView = view.findViewById<SearchView>(R.id.svBuscar)

        //configureSearchview (svBuscar)

        val exeRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        val progress = view.findViewById<ProgressBar>(R.id.progress)

        val newExercise = view.findViewById<ImageButton>(R.id.newExercise)

        controladores()
        observadorViewModel()



    }
    private fun controladores() {
        binding.newExercise.setOnClickListener {
            //findNavController().navigate(R.id.action_homeExerciseFragment_to_addExerciseFragment)
        }

    }

    private fun observadorViewModel(){
        observerListExercise()
        observerProgress()

    }

    private fun observerListExercise() {
        lifecycleScope.launch {
            val listExercise = exerciseViewModel.getListExercises()
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = ExercisesAdapter(listExercise, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
           }
    }

    private fun observerProgress(){
        exerciseViewModel.progresState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
        }
    }


    /*private  fun configureSearchview(searchView: SearchView){


        //Haciendo la consulta en el cuadro de busqueda:
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Ejecuta la busqueda al dar clic en el boton buscar del teclado por defecto:
                if(!query.isNullOrEmpty()){
                    Toast.makeText( "Buscar : $query", Toast.LENGTH_LONG).show()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //Detecta las letras que se introducen en la caja y hace el filtro y lo lista
                adapter.filter.filter(query)
                return true
            }
        })
    }*/


}
