package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page33 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var SkoolGraadEditText: EditText
    private lateinit var InstrumentEditText: EditText
    private lateinit var BegSTJEditText: EditText
    private lateinit var BKSTJEditText: EditText
    private lateinit var RMSTJEditText: EditText
    private lateinit var GroepSolosSTJEditText: EditText
    private lateinit var ConcertoSTJEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page33)
        //
        val entryDescription = "Strykinstrumente (Strings) Junior  Solo"
        vanneNameEditText = findViewById(R.id.editTextText158)
        SkoolGraadEditText = findViewById(R.id.editTextText170)
        InstrumentEditText = findViewById(R.id.editTextText169)
        BegSTJEditText = findViewById(R.id.editTextText171)
        BKSTJEditText = findViewById(R.id.editTextText172)
        RMSTJEditText = findViewById(R.id.editTextText167)
        GroepSolosSTJEditText = findViewById(R.id.editTextText162)
        ConcertoSTJEditText = findViewById(R.id.editTextText168)
        FaceToFaceCheckBox = findViewById(R.id.checkBox57)
        VideoCheckBox = findViewById(R.id.checkBox58)
        val button: Button = findViewById(R.id.button28)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val SkoolGraad = SkoolGraadEditText.text.toString()
            val Instrument = InstrumentEditText.text.toString()
            val BegSTJ = BegSTJEditText.text.toString()
            val BKSTJ = BKSTJEditText.text.toString()
            val RMSTJ = RMSTJEditText.text.toString()
            val GroepSolosSTJ = GroepSolosSTJEditText.text.toString()
            val ConcertoSTJ = ConcertoSTJEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, Instrument, BegSTJ, BKSTJ, RMSTJ, GroepSolosSTJ, ConcertoSTJ, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, Instrument, BegSTJ, BKSTJ, RMSTJ, GroepSolosSTJ, ConcertoSTJ, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }
    //

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, SkoolGraad: String, Instrument: String, BegSTJ: String, BKSTJ: String, RMSTJ: String, GroepSolosSTJ: String, ConcertoSTJ: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val SkoolGraad: String,
            val Instrument: String,
            val BegSTJ: String,
            val BKSTJ: String,
            val RMSTJ: String,
            val GroepSolosSTJ: String,
            val ConcertoSTJ: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            SkoolGraad,
            Instrument,
            BegSTJ,
            BKSTJ,
            RMSTJ,
            GroepSolosSTJ,
            ConcertoSTJ,
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
        Toast.makeText(this@Page33, message, Toast.LENGTH_SHORT).show()
    }
}