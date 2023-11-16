package com.example.kae

import android.media.FaceDetector.Face
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page2 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var blokfluitEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page2)

        //
        vanneNameEditText = findViewById(R.id.editTextText7)
        blokfluitEditText = findViewById(R.id.editTextText8)
        FaceToFaceCheckBox = findViewById(R.id.checkBox5)
        VideoCheckBox = findViewById(R.id.checkBox6)
        //
        val button: Button = findViewById(R.id.button13)

//SUBMIT BUTTON
        button.setOnClickListener {
            val entryDescription = "Blokfluit (Recorder) Ensembles"
            val vanneEnName = vanneNameEditText.text.toString()
            val blokfluit = blokfluitEditText.text.toString()

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, blokfluit, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, blokfluit, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    private fun submitDataToFirebase(description: String, vanneEnName: String, blokfluit: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val blokfluit: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            blokfluit,
            FaceToFace
        )

        // Reference to the "Blokfluit (Recorder) Ensembles" node
        val blokfluitNode = reference.child(description)

        // Read current number of children to determine the next key
        blokfluitNode.get().addOnSuccessListener { snapshot ->
            val nextKey = (snapshot.childrenCount + 1).toString()

            // Specify path using child() method with the next key
            val nextPath = blokfluitNode.child(nextKey)

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
        Toast.makeText(this@Page2, message, Toast.LENGTH_SHORT).show()
    }
}