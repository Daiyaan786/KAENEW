package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page21 : AppCompatActivity() {


    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var GraadEditText: EditText
    private lateinit var PoetryPOEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page21)
        //
        val entryDescription = "Poetry Senior"
        vanneNameEditText = findViewById(R.id.editTextText94)
        GraadEditText = findViewById(R.id.editTextText95)
        PoetryPOEditText = findViewById(R.id.editTextText96)
        val button: Button = findViewById(R.id.button15)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val Graad = GraadEditText.text.toString()
            val PoetryPO = PoetryPOEditText.text.toString()
            //
            submitDataToFirebase(entryDescription, vanneEnName, Graad, PoetryPO)
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, Graad: String, PoetryPO: String) {
        data class DataClass(
            val vanneEnName: String,
            val Graad: String,
            val PoetryPO: String
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            Graad,
            PoetryPO
        )

        // Reference to the node
        val Node = reference.child(description)

        // Read current number of children to determine the next key
        Node.get().addOnSuccessListener { snapshot ->
            val nextKey = (snapshot.childrenCount + 1).toString()

            // Specify path using child() method with the next key
            val nextPath = Node.child(nextKey)

            // Push data to specified path
            nextPath.setValue(userData)
                .addOnSuccessListener{
                    // Handle success
                    showToast("Great, the info was successfully submitted.")
                }
                .addOnFailureListener{
                    // Handle failure
                    showToast("Unfortunately, there was an error.")
                }
        }.addOnFailureListener { error ->
            // Handle failure [ if needed ]
        }
    }

    // Function to show a Toast message
    private fun showToast(message: String) {
        Toast.makeText(this@Page21, message, Toast.LENGTH_SHORT).show()
    }
}