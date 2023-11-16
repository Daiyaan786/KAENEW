package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page11 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var skoolGraadEditText: EditText
    private lateinit var BeginnersKJEditText: EditText
    private lateinit var BKKJEditText: EditText
    private lateinit var RMKJEditText: EditText
    private lateinit var GroepSolosRecitalsEditText: EditText
    private lateinit var UnisaUNEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page11)
        //
        val entryDescription = "Klavier (Piano) Junior Graad R-5 Solo"
        vanneNameEditText = findViewById(R.id.editTextText48)
        skoolGraadEditText = findViewById(R.id.editTextText49)
        BeginnersKJEditText = findViewById(R.id.editTextText50)
        BKKJEditText = findViewById(R.id.editTextText51)
        RMKJEditText = findViewById(R.id.editTextText52)
        GroepSolosRecitalsEditText = findViewById(R.id.editTextText53)
        UnisaUNEditText = findViewById(R.id.editTextText54)
        FaceToFaceCheckBox = findViewById(R.id.checkBox21)
        VideoCheckBox = findViewById(R.id.checkBox22)
        val button: Button = findViewById(R.id.button4)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val skoolGraad = skoolGraadEditText.text.toString()
            val BeginnersKJ = BeginnersKJEditText.text.toString()
            val BKKJ = BKKJEditText.text.toString()
            val RMKJ = RMKJEditText.text.toString()
            val GroepSolosRecitals = GroepSolosRecitalsEditText.text.toString()
            val UnisaUN = UnisaUNEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BeginnersKJ, BKKJ, RMKJ, GroepSolosRecitals, UnisaUN, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, BeginnersKJ, BKKJ, RMKJ, GroepSolosRecitals, UnisaUN, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, skoolGraad: String, BeginnersKJ: String, BKKJ: String, RMKJ: String, GroepSolosRecitals: String, UnisaUN: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val skoolGraad: String,
            val BeginnersKJ: String,
            val BKKJ: String,
            val RMKJ: String,
            val GroepSolosRecitals: String,
            val UnisaUN: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            skoolGraad,
            BeginnersKJ,
            BKKJ,
            RMKJ,
            GroepSolosRecitals,
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
        Toast.makeText(this@Page11, message, Toast.LENGTH_SHORT).show()
    }
}