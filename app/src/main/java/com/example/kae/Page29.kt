package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page29 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var GraadEditText: EditText
    private lateinit var GedigAGEditText: EditText
    private lateinit var ProsaAPEditText: EditText
    private lateinit var LeesALEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page29)
        //
        val entryDescription = "Spraak Addisionele Taal Senior"
        vanneNameEditText = findViewById(R.id.editTextText133)
        GraadEditText = findViewById(R.id.editTextText136)
        GedigAGEditText = findViewById(R.id.editTextText135)
        ProsaAPEditText = findViewById(R.id.editTextText128)
        LeesALEditText = findViewById(R.id.editTextText134)
        FaceToFaceCheckBox = findViewById(R.id.checkBox48)
        VideoCheckBox = findViewById(R.id.checkBox47)
        val button: Button = findViewById(R.id.button23)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val Graad = GraadEditText.text.toString()
            val GedigAG = GedigAGEditText.text.toString()
            val ProsaAP = ProsaAPEditText.text.toString()
            val LeesAL = LeesALEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, Graad, GedigAG, ProsaAP, LeesAL, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, Graad, GedigAG, ProsaAP, LeesAL, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, Graad: String, GedigAG: String, ProsaAP: String, LeesAL: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val Graad: String,
            val GedigAG: String,
            val ProsaAP: String,
            val LeesAL: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            Graad,
            GedigAG,
            ProsaAP,
            LeesAL,
            FaceToFace
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
        Toast.makeText(this@Page29, message, Toast.LENGTH_SHORT).show()
    }
}