package com.example.equipodos.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.equipodos.R
import com.example.equipodos.databinding.ActivityRegistroBinding
import com.example.equipodos.viewmodel.LoginViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import android.content.Intent
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFirebase()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registro)

        val passwordTextInputLayout = findViewById<TextInputLayout>(R.id.tilPassword)
        val passwordEditText = findViewById<TextInputEditText>(R.id.etPassword)
        val cpasswordTextInputLayout = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val cpasswordEditText = findViewById<TextInputEditText>(R.id.etConfirmPassword)

        passwordTextInputLayout.setOnClickListener {
            togglePasswordVisibility(passwordEditText)
        }

        cpasswordTextInputLayout.setOnClickListener {
            togglePasswordVisibility(cpasswordEditText)
        }

        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        sesion()
        setup()
    }

    private fun togglePasswordVisibility(editText: EditText) {
        if (editText.transformationMethod == null) {

            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {

            editText.transformationMethod = null
        }

        editText.setSelection(editText.text.length)
    }


    private fun goToHome(email: String?) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
        finish()
    }


    private fun registerUser() {
        val email = binding.etCorreo.text.toString()
        val pass = binding.etPassword.text.toString()
        val confirmpass = binding.etConfirmPassword.text.toString()
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()

        if (email.isEmpty() || pass.isEmpty() || confirmpass.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            Toast.makeText(this, "Faltan datos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (!email.contains("@")) {
            Toast.makeText(this, "Debe ingresar un correo válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass.length < 6) {
            Toast.makeText(
                this,
                "La contraseña debe tener al menos 6 caracteres",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (pass == confirmpass) {
            loginViewModel.registerUser(email, pass, nombre, apellido) { isRegister ->
                if (isRegister) {
                    goToHome(email)
                } else {
                    Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeFirebase() {
        FirebaseApp.initializeApp(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
    }

    private fun setup() {
        binding.btnRegistro.setOnClickListener {
            registerUser()
        }
    }

    private fun sesion() {
        val email = sharedPreferences.getString("email", null)
        loginViewModel.sesion(email) { isEnableView ->
            if (isEnableView) {
                binding.reContenedor.visibility = View.INVISIBLE
                goToHome(email)
            }
        }
    }
}