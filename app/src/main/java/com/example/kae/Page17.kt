package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page17 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var skoolGraadEditText: EditText
    private lateinit var BKKBEditText: EditText
    private lateinit var RMKBEditText: EditText
    private lateinit var JazzKBEditText: EditText
    private lateinit var GroepSolosKBEditText: EditText
    private lateinit var BeginnersEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page17)
        //
        val entryDescription = "Koperblaasinstrumente (Brass Instruments) Solo"
        vanneNameEditText = findViewById(R.id.editTextText84)
        skoolGraadEditText = findViewById(R.id.editTextText85)
        BKKBEditText = findViewById(R.id.editTextText86)
        RMKBEditText = findViewById(R.id.editTextText87)
        JazzKBEditText = findViewById(R.id.editTextText88)
        GroepSolosKBEditText = findViewById(R.id.editTextText89)
        BeginnersEditText = findViewById(R.id.editTextText90)
        FaceToFaceCheckBox = findViewById(R.id.checkBox36)
        VideoCheckBox = findViewById(R.id.checkBox35)
        val button: Button = findViewById(R.id.button10)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val skoolGraad = skoolGraadEditText.text.toString()
            val BKKB = BKKBEditText.text.toString()
            val RMKB = RMKBEditText.text.toString()
            val JazzKB = JazzKBEditText.text.toString()
            val GroepSolosKB = GroepSolosKBEditText.text.toString()
            val Beginners = BeginnersEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BKKB, RMKB, JazzKB, GroepSolosKB, Beginners, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BKKB, RMKB, JazzKB, GroepSolosKB, Beginners, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, skoolGraad: String, BKKB: String, RMKB: String, JazzKB: String, GroepSolosKB: String, Beginners: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val skoolGraad: String,
            val BKKB: String,
            val RMKB: String,
            val JazzKB: String,
            val GroepSolosKB: String,
            val Beginners: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            skoolGraad,
            BKKB,
            RMKB,
            JazzKB,
            GroepSolosKB,
            Beginners,
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
        Toast.makeText(this@Page17, message, Toast.LENGTH_SHORT).show()
    }
}