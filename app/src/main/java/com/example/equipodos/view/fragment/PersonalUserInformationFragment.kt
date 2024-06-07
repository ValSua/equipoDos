package com.example.equipodos.view.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.equipodos.R
import com.example.equipodos.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class PersonalUserInformationFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_user_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setup()
        loadUserInformationFromFirebaseAuth(view)
    }

    private fun loadUserInformationFromFirebaseAuth(view: View) {
        val firebaseUser = auth.currentUser
        var userName = ""
        var userLastName = ""
        val email = firebaseUser?.email.toString()


        viewModel.obtenerNombreApellidoUsuario(email) { nombre, apellido ->
            if (nombre != null && apellido != null) {
                // Utilizar el nombre y apellido obtenidos del ViewModel
                userName = nombre
                userLastName = apellido
                view.findViewById<EditText>(R.id.nombre).setText(userName)
                view.findViewById<EditText>(R.id.apellido).setText(userLastName)
            } else {
                // Manejar el caso en que no se pueda obtener el nombre y apellido
                println("No se pudo obtener el nombre y apellido del usuario.")
            }
        }

        view.findViewById<EditText>(R.id.editText3).setText(email)
    }

    private fun setup() {
        view?.findViewById<View>(R.id.informacion_del_usario_toolbar)?.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
