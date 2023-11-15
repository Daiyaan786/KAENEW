package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        registerButton = findViewById(R.id.buttonRegister)

        registerButton.setOnClickListener {
            // Implement your registration logic here
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            registerButton.setOnClickListener {
                // Handle the Login button click here
                val loginIntent = Intent(this, Burgermenu::class.java)
                startActivity(loginIntent)

            // Perform user registration, and if successful, navigate to another activity.
            // For example:
            // if (registerUser(email, password)) {
            //     val intent = Intent(this, LoginActivity::class.java)
            //     startActivity(intent)
            // } else {
            //     // Show an error message to the user
            // }
        }
    }

    // You can define your registration logic here.
    // For example:
    // private fun registerUser(email: String, password: String): Boolean {
    //     // Implement your user registration logic here
    //     return true // Return true if registration is successful
    // }
}}
