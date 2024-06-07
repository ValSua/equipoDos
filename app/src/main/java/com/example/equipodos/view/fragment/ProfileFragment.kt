package com.example.equipodos.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.equipodos.R
import com.example.equipodos.databinding.FragmentProfileBinding
import com.example.equipodos.view.HomeActivity
import com.example.equipodos.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
    }

    private fun setup() {
        binding.viewPersonalInformation.setOnClickListener {
            findNavController().navigate(R.id.personalUserInformationFragment)
        }

        binding.btnSignOut.setOnClickListener {
            logOut()
        }

        binding.btnRutinasnuevo.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnPerfilself.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_self)
        }


    }

    private fun logOut() {
        sharedPreferences.edit().clear().apply()
        FirebaseAuth.getInstance().signOut()
        (requireActivity() as HomeActivity).apply {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}