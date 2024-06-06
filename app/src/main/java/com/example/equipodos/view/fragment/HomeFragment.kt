package com.example.equipodos.view.fragment

import RoutineViewModel
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.equipodos.R
import com.example.equipodos.databinding.FragmentHomeBinding
import com.example.equipodos.model.Articulo
import com.example.equipodos.model.Routine
import com.example.equipodos.view.HomeActivity
import com.example.equipodos.view.LoginActivity
import com.example.equipodos.view.RoutineAdapter
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RoutineViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var routineAdapter: RoutineAdapter
    private val routinesList: MutableList<Pair<String, String>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root}

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
//        dataLogin()
        setup()
//        listarProducto()

        viewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        auth = FirebaseAuth.getInstance()

        val usuario = auth.currentUser
        val email = usuario?.email.toString()

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.rutinas)
        routineAdapter = RoutineAdapter(routinesList)
        recyclerView.adapter = routineAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        // Obtener rutinas y actualizar la lista
        viewModel.obtenerIdRutinas(email) { rutinas ->
            rutinas?.let {
                routinesList.clear()
                routinesList.addAll(it)
                routineAdapter.notifyDataSetChanged()


            }

//            if (routinesList.isEmpty()) {
//                view.findViewById<ImageView>(R.id.crearRutina).visibility = View.VISIBLE
//            } else {
//                view.findViewById<ImageView>(R.id.crearRutina).visibility = View.GONE
//            }
        }

    }



    private fun setup() {
//        binding.btnLogOut.setOnClickListener {
//            logOut()
//        }




        // En el Fragmento Origen (HomeFragment)
        binding.nuevarutina.setOnClickListener {
            val projectId = FirebaseApp.getInstance().options.projectId
            val bundle = Bundle().apply {
                putString("projectId", projectId)
            }
            findNavController().navigate(R.id.action_homeFragment_to_createRutineFragment)
        }



    }




//    private fun listarProducto(){
//        db.collection("articulo").get().addOnSuccessListener {
//            var data = ""
//            for (document in it.documents) {
//                // Aquí puedes personalizar cómo deseas mostrar cada artículo en la lista
//                data += "Código: ${document.get("codigo")} " +
//                        "Nombre: ${document.get("nombre")} " +
//                        "Precio: ${document.get("precio")}\n\n"
//
//            }
//            binding.tvListProducto.text = data
//        }
//    }

//    private fun dataLogin() {
//        val bundle = requireActivity().intent.extras
//        val email = bundle?.getString("email")
//        binding.tvTitleEmail.text = email ?: ""
//        sharedPreferences.edit().putString("email",email).apply()
//    }
//
//    private fun logOut() {
//        sharedPreferences.edit().clear().apply()
//        FirebaseAuth.getInstance().signOut()
//        (requireActivity() as HomeActivity).apply {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//    }
//
//    private fun limpiarCampos() {
//        binding.etCodigo.setText("")
//        binding.etNombreArticulo.setText("")
//        binding.etPrecio.setText("")
//    }
}