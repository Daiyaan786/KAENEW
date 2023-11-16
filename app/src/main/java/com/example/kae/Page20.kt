package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page20 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var GraadEditText: EditText
    private lateinit var GedigGEEditText: EditText
    private lateinit var ProsaPEEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page20)
        //
        val entryDescription = "Letterkunde Junior"
        vanneNameEditText = findViewById(R.id.editTextText83)
        GraadEditText = findViewById(R.id.editTextText91)
        GedigGEEditText = findViewById(R.id.editTextText92)
        ProsaPEEditText = findViewById(R.id.editTextText93)
        val button: Button = findViewById(R.id.button14)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val Graad = GraadEditText.text.toString()
            val GedigGE = GedigGEEditText.text.toString()
            val ProsaPE = ProsaPEEditText.text.toString()
            //
            submitDataToFirebase(entryDescription, vanneEnName, Graad, GedigGE, ProsaPE)
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, Graad: String, GedigGE: String, ProsaPE: String) {
        data class DataClass(
            val vanneEnName: String,
            val Graad: String,
            val GedigGE: String,
            val ProsaPE: String
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            Graad,
            GedigGE,
            ProsaPE
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
        Toast.makeText(this@Page20, message, Toast.LENGTH_SHORT).show()
    }
}