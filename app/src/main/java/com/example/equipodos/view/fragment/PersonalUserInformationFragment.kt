package com.example.equipodos.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.equipodos.R
import com.example.equipodos.databinding.FragmentPersonalUserInformationBinding
import com.example.equipodos.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class PersonalUserInformationFragment : Fragment() {
    private lateinit var nombreUsuario: String
    private lateinit var nombreApellido: List<String>
    private lateinit var correo: String
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentPersonalUserInformationBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalUserInformationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
        loadUserInformationFromFirebaseAuth(view)
    }

    private fun loadUserInformationFromFirebaseAuth(view: View) {
        val firebaseUser = auth.currentUser
        val userFullName = firebaseUser?.displayName.toString()
        val fullNameComponents = userFullName.split(" ")
        val userName = fullNameComponents[0]

        if (fullNameComponents.size > 1) {
            val userLastName = fullNameComponents[1]
        } else {
           val userLastName = ""
        }

        val editTexts = listOf(
            view.findViewById<EditText>(R.id.editText),
            view.findViewById<EditText>(R.id.editText2),
            view.findViewById<EditText>(R.id.editText3)
        )

        for (editText in editTexts) {
            if (editText.id == R.id.editText) {
                editText.setText(userName)
            } else if (editText.id == R.id.editText2) {
                if (fullNameComponents.size > 1) {
                    editText.setText(fullNameComponents[1])
                } else {
                    editText.setText("")
                }
            } else if (editText.id == R.id.editText3) {
                editText.setText(firebaseUser?.email.toString())
            }
        }
    }

    private fun setup() {
        auth = FirebaseAuth.getInstance()
        binding.informacionDelUsarioToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}