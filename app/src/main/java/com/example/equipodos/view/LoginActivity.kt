package com.example.equipodos.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.equipodos.R
import com.example.equipodos.databinding.ActivityLoginBinding
import com.example.equipodos.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFirebase()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val passwordTextInputLayout = findViewById<TextInputLayout>(R.id.tilPass)
        val passwordEditText = findViewById<TextInputEditText>(R.id.etPass)

        passwordTextInputLayout.setOnClickListener {
            togglePasswordVisibility(passwordEditText)
        }

        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        sesion()
        setup()
    }

    private fun togglePasswordVisibility(editText: EditText) {
        if (editText.transformationMethod == null) {
            // Si la contraseña es visible, ocúltala
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            // Si la contraseña está oculta, muéstrala
            editText.transformationMethod = null
        }

        // Mueve el cursor al final del texto
        editText.setSelection(editText.text.length)
    }

    private fun initializeFirebase() {
        FirebaseApp.initializeApp(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
    }

    private fun setup() {


        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvRegister.setOnClickListener {
            // Iniciar la RegistroActivity cuando se haga clic en tvRegister
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }


    private fun goToHome(email: String?) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
        finish()
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()
        loginViewModel.loginUser(email, pass) { isLogin ->
            if (isLogin) {
                goToHome(email)
            } else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sesion() {
        val email = sharedPreferences.getString("email", null)
        loginViewModel.sesion(email) { isEnableView ->
            if (isEnableView) {
                binding.clContenedor.visibility = View.INVISIBLE
                goToHome(email)
            }
        }
    }
//    override fun onStart() {
//        super.onStart()
//        binding.clContenedor.visibility = View.VISIBLE
//    }
}