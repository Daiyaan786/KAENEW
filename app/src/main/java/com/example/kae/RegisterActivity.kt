package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.kae.R.id.btnRegisterBackBtn
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest
import java.security.SecureRandom


class RegisterActivity : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var EmailEditText: EditText
    private lateinit var PasswordEditText: EditText
    private lateinit var NameEditText: EditText
    private lateinit var SchoolEditText: EditText
    private lateinit var SchoolPhoneNumEditText: EditText
    private lateinit var PhoneNumEditText: EditText
    private lateinit var SchoolAddressEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        EmailEditText = findViewById(R.id.editTextEmail)
        PasswordEditText = findViewById(R.id.editTextPassword)
        NameEditText = findViewById(R.id.editTextName)
        SchoolEditText = findViewById(R.id.editTextSchool)
        SchoolPhoneNumEditText = findViewById(R.id.editTextSchoolContact)
        PhoneNumEditText = findViewById(R.id.editTextYourContact)
        SchoolAddressEditText = findViewById(R.id.editTextSchoolAddress)
        registerButton = findViewById(R.id.buttonRegister)

        val backButton = findViewById<View>(R.id.btnRegisterBackBtn)

        //BACK BUTTON TO MAINACTIVITY
        backButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }
        //

        //REGISTER BUTTON
        registerButton.setOnClickListener {
            //
            val Email = EmailEditText.text.toString()
            val Password = PasswordEditText.text.toString()
            val Name = NameEditText.text.toString()
            val School = SchoolEditText.text.toString()
            val SchoolPhoneNum = SchoolPhoneNumEditText.text.toString()
            val PhoneNum = PhoneNumEditText.text.toString()
            val SchoolAddress = SchoolAddressEditText.text.toString()

            // Perform user registration
            if (Email.isNotEmpty() && Password.isNotEmpty() && Name.isNotEmpty() &&
                School.isNotEmpty() && SchoolPhoneNum.isNotEmpty() &&
                PhoneNum.isNotEmpty() && SchoolAddress.isNotEmpty()
            ) {
                registerUser(Email, Password, Name, School, SchoolPhoneNum, PhoneNum, SchoolAddress)
            } else {
                //Error Message
                showToast("Please fill in ALL Information.")
            }
        }
    }

    // FUNCTION TO REGISTER USER
    private fun registerUser(Email: String, Password: String, Name: String, School: String, SchoolPhoneNum: String, PhoneNum: String, SchoolAddress: String) {
        data class DataClass(
            val Email: String,
            val Name: String,
            val School: String,
            val SchoolPhoneNum: String,
            val PhoneNum: String,
            val SchoolAddress: String,
            val HashedPassword: String,
            val SaltString: String
        )
        // Generate a random salt
        val salt = generateSalt()
        val saltString = encodeBase64(salt)
        // Combine the password and salt, and then hash the result
        val hashedPassword = hashPassword(Password, salt)

        // Replace with your data
        val userData = DataClass(
            Email,
            Name,
            School,
            SchoolPhoneNum,
            PhoneNum,
            SchoolAddress,
            hashedPassword,
            saltString
        )

        // Reference to the node
        val Node = reference.child("Teachers")

        // Read current number of children to determine the next key
        Node.get().addOnSuccessListener { snapshot ->
            val nextKey = (snapshot.childrenCount + 1).toString()

            // Specify path using child() method with the next key
            val nextPath = Node.child(nextKey)

            // Push data to specified path
            nextPath.setValue(userData)
                .addOnSuccessListener{
                    showToast("Registration Successful!")
                    // Success
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { error ->
                    // Failure
                    showToast("Registration failed. Error: ${error.message}")
                }
        }.addOnFailureListener { error ->
            // Failure
            showToast("Unfortunately, there was an error. Error: ${error.message}")
        }

     }
    // Function to Generate a random salt
    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
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

    // Function to Convert the byte array to a Base64-encoded string
    private fun encodeBase64(data: ByteArray): String {
        return android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }
}
