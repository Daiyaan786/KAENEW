package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.input.nestedscroll.nestedScrollModifierNode
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page30 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var GraadEditText: EditText
    private lateinit var GedigJVGEditText: EditText
    private lateinit var ProsaJVPEditText: EditText
    private lateinit var MondKommMKEditText: EditText
    private lateinit var LeesJOLEditText: EditText
    private lateinit var MimiekSoloJMMEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page30)
        //
        val entryDescription = "Spraak en Mimiek Junior"
        vanneNameEditText = findViewById(R.id.editTextText145)
        GraadEditText = findViewById(R.id.editTextText146)
        GedigJVGEditText = findViewById(R.id.editTextText144)
        ProsaJVPEditText = findViewById(R.id.editTextText137)
        MondKommMKEditText = findViewById(R.id.editTextText143)
        LeesJOLEditText = findViewById(R.id.editTextText147)
        MimiekSoloJMMEditText = findViewById(R.id.editTextText142)
        FaceToFaceCheckBox = findViewById(R.id.checkBox52)
        VideoCheckBox = findViewById(R.id.checkBox51)
        val button: Button = findViewById(R.id.button25)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val Graad = GraadEditText.text.toString()
            val GedigJVG = GedigJVGEditText.text.toString()
            val ProsaJVP = ProsaJVPEditText.text.toString()
            val MondKommMK = MondKommMKEditText.text.toString()
            val LeesJOL = LeesJOLEditText.text.toString()
            val MimiekSoloJMM = MimiekSoloJMMEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, Graad, GedigJVG, ProsaJVP, MondKommMK, LeesJOL, MimiekSoloJMM, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, Graad, GedigJVG, ProsaJVP, MondKommMK, LeesJOL, MimiekSoloJMM, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, Graad: String, GedigJVG: String, ProsaJVP: String, MondKommMK: String, LeesJOL: String, MimiekSoloJMM: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val Graad: String,
            val GedigJVG: String,
            val ProsaJVP: String,
            val MondKommMK: String,
            val LeesJOL: String,
            val MimiekSoloJMM: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            Graad,
            GedigJVG,
            ProsaJVP,
            MondKommMK,
            LeesJOL,
            MimiekSoloJMM,
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
        Toast.makeText(this@Page30, message, Toast.LENGTH_SHORT).show()
    }
}