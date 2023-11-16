package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page4 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var skoolEditText: EditText
    private lateinit var getalSpelersEditText: EditText
    private lateinit var kleinEnsEditText: EditText
    private lateinit var grootEnsEditText: EditText
    private lateinit var orkesEditText: EditText
    private lateinit var jazzOrkesEditText: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page4)
        //
        val entryDescription = "Ensembles en Orkeste"
        vanneNameEditText = findViewById(R.id.editTextText6)
        skoolEditText = findViewById(R.id.editTextText17)
        getalSpelersEditText = findViewById(R.id.editTextText18)
        kleinEnsEditText = findViewById(R.id.editTextText19)
        grootEnsEditText = findViewById(R.id.editTextText20)
        orkesEditText = findViewById(R.id.editTextText21)
        jazzOrkesEditText = findViewById(R.id.editTextText22)
        val button: Button = findViewById(R.id.button34)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val skool = skoolEditText.text.toString()
            val getalSpelers = getalSpelersEditText.text.toString()
            val kleinEns = kleinEnsEditText.text.toString()
            val grootEns = grootEnsEditText.text.toString()
            val orkes = orkesEditText.text.toString()
            val jazzOrkes = jazzOrkesEditText.text.toString()
            //
            submitDataToFirebase(entryDescription,vanneEnName, skool, getalSpelers, kleinEns, grootEns, orkes, jazzOrkes)
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, skool: String, getalSpelers: String, kleinEns: String, grootEns: String, orkes: String, jazzOrkes: String) {
        data class DataClass(
            val vanneEnName: String,
            val skool: String,
            val getalSpelers: String,
            val kleinEns: String,
            val grootEns: String,
            val orkes: String,
            val jazzOrkes: String
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            skool,
            getalSpelers,
            kleinEns,
            grootEns,
            orkes,
            jazzOrkes
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
        Toast.makeText(this@Page4, message, Toast.LENGTH_SHORT).show()
    }
}