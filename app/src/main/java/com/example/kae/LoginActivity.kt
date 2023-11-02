package com.example.kae

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.view.View
import com.example.kae.R.id.btnLoginBackBtn
import android.content.Context
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)

        val backButton = findViewById<View>(R.id.btnLoginBackBtn)
//BACK BUTTON TO MAINACTIVITY
//
        backButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent) }
//LOGIN BUTTON
//
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // LOGIN AUTHENTICATION CHECK
             if (isValidUser(email, password)) {
                 val intent = Intent(this, Burgermenu::class.java)
                 startActivity(intent)
             } else {
                 showMessage(this, "Incorrect Details.") // error message
             }
        }
    }

//ShowMessage Function
    private fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
// Authentication Logic
    private fun isValidUser(email: String, password: String): Boolean {
         // Authentication
        return email == "admin" && password == "admin123"
     }
}