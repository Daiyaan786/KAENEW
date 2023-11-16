package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page9 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var skoolGraadEditText: EditText
    private lateinit var BKGEditText: EditText
    private lateinit var RMGEditText: EditText
    private lateinit var GroepSolosGEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page9)
        //
        val entryDescription = "Klassieke Kitaar (Classical Guitar) Solo"
        vanneNameEditText = findViewById(R.id.editTextText36)
        skoolGraadEditText = findViewById(R.id.editTextText37)
        BKGEditText = findViewById(R.id.editTextText38)
        RMGEditText = findViewById(R.id.editTextText39)
        GroepSolosGEditText = findViewById(R.id.editTextText40)
        FaceToFaceCheckBox = findViewById(R.id.checkBox17)
        VideoCheckBox = findViewById(R.id.checkBox18)
        val button: Button = findViewById(R.id.button39)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val skoolGraad = skoolGraadEditText.text.toString()
            val BKG = BKGEditText.text.toString()
            val RMG = RMGEditText.text.toString()
            val GroepSolosG = GroepSolosGEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BKG, RMG, GroepSolosG, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BKG, RMG, GroepSolosG, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, skoolGraad: String, BKG: String, RMG: String, GroepSolosG: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val skoolGraad: String,
            val BKG: String,
            val RMG: String,
            val GroepSolosG: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            skoolGraad,
            BKG,
            RMG,
            GroepSolosG,
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
        Toast.makeText(this@Page9, message, Toast.LENGTH_SHORT).show()
    }
}