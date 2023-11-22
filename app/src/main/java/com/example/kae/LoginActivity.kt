package com.example.kae

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // a root *node* reference
    private val reference: DatabaseReference = database.getReference()

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
            //
            (isValidUser(email, password))
        }
    }

//ShowMessage Function
    private fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
// Authentication Logic
private fun isValidUser(email: String, password: String) {
    val teachersRef = reference.child("Teachers")

    // Query the database to find matching email and password
    teachersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
        ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Check if the email exists
            if (dataSnapshot.exists()) {
                // Iterate through the results
                for (teacherSnapshot in dataSnapshot.children) {
                    // Get the stored password
                    val storedPassword = teacherSnapshot.child("password").getValue(String::class.java)

                    // Check if the provided password matches the stored password
                    if (storedPassword == password) {
                        // Authentication successful
                        val teacherId = teacherSnapshot.key
                        TeacherIdHolder.teacherId = teacherId

                        showMessage(this@LoginActivity, "Login Successful.")
                        //
                        val intent = Intent(this@LoginActivity, EntryForm::class.java)
                        startActivity(intent)
                        return

                    }
                }
            }

            // Authentication failed
            showMessage(this@LoginActivity, "Incorrect Details.")
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Handle errors here
        }
    })
}

}