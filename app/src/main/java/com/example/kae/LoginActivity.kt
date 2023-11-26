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
import java.security.MessageDigest


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
                    // Get the stored hashed password and salt as Base64-encoded strings
                    val storedHashedPassword =
                        teacherSnapshot.child("hashedPassword").getValue(String::class.java)
                    val storedSaltString = teacherSnapshot.child("saltString").getValue(String::class.java)

                    // Convert the salt back to a byte array
                    val storedSalt = decodeBase64(storedSaltString)

                    // Combine the provided password and stored salt, and then hash the result
                    val newHashedPassword = hashPassword(password, storedSalt)

                    // Check if the provided password matches the stored password
                    if (newHashedPassword == storedHashedPassword) {
                        // Authentication successful
                        val teacherId = teacherSnapshot.key
                        TeacherIdHolder.teacherId = teacherId

                        showMessage(this@LoginActivity, "Login Successful.")
                        //
                        val intent = Intent(this@LoginActivity, StartPage::class.java)
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
    // Function to Combine the password and salt, and then hash the result
    private fun hashPassword(password: String, salt: ByteArray): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(salt)
        val hashedBytes = md.digest(password.toByteArray(Charsets.UTF_8))

        // Convert the byte array to a hexadecimal string
        val stringBuilder = StringBuilder()
        for (byte in hashedBytes) {
            stringBuilder.append(String.format("%02x", byte))
        }
        return stringBuilder.toString()
    }

    // Function to ReConvert the Base64-encoded string to a byte array
    private fun decodeBase64(data: String?): ByteArray {
        return android.util.Base64.decode(data, android.util.Base64.DEFAULT)
    }

}