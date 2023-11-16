package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page13 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var skoolGraadEditText: EditText
    private lateinit var BKKEditText: EditText
    private lateinit var RMKEditText: EditText
    private lateinit var JazzKEditText: EditText
    private lateinit var GroepSolosKEditText: EditText
    private lateinit var JSBachKEditText: EditText
    private lateinit var SonateKEditText: EditText
    private lateinit var UnisaUNEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page13)
        //
        val entryDescription = "Klavier (Piano), Senior Solo"
        vanneNameEditText = findViewById(R.id.editTextText58)
        skoolGraadEditText = findViewById(R.id.editTextText59)
        BKKEditText = findViewById(R.id.editTextText60)
        RMKEditText = findViewById(R.id.editTextText61)
        JazzKEditText = findViewById(R.id.editTextText62)
        GroepSolosKEditText = findViewById(R.id.editTextText63)
        JSBachKEditText = findViewById(R.id.editTextText64)
        SonateKEditText = findViewById(R.id.editTextText65)
        UnisaUNEditText = findViewById(R.id.editTextText66)
        FaceToFaceCheckBox = findViewById(R.id.checkBox25)
        VideoCheckBox = findViewById(R.id.checkBox26)
        val button: Button = findViewById(R.id.button6)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val skoolGraad = skoolGraadEditText.text.toString()
            val BKK = BKKEditText.text.toString()
            val RMK = RMKEditText.text.toString()
            val JazzK = JazzKEditText.text.toString()
            val GroepSolosK = GroepSolosKEditText.text.toString()
            val JSBachK = JSBachKEditText.text.toString()
            val SonateK = SonateKEditText.text.toString()
            val UnisaUN = UnisaUNEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BKK, RMK, JazzK, GroepSolosK, JSBachK, SonateK, UnisaUN, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BKK, RMK, JazzK, GroepSolosK, JSBachK, SonateK, UnisaUN, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, skoolGraad: String, BKK: String, RMK: String, JazzK: String, GroepSolosK: String, JSBachK: String, SonateK: String, UnisaUN: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val skoolGraad: String,
            val BKK: String,
            val RMK: String,
            val JazzK: String,
            val GroepsolosK: String,
            val JSBachK: String,
            val SonateK: String,
            val UnisaUN: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            skoolGraad,
            BKK,
            RMK,
            JazzK,
            GroepSolosK,
            JSBachK,
            SonateK,
            UnisaUN,
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
        Toast.makeText(this@Page13, message, Toast.LENGTH_SHORT).show()
    }
}