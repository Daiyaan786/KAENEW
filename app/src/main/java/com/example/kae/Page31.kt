package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page31 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var SkoolEditText: EditText
    private lateinit var GraadEditText: EditText
    private lateinit var EkspProgramJEPEditText: EditText
    private lateinit var GroepLeesJVLEditText: EditText
    private lateinit var KoorspraakJKSEditText: EditText
    private lateinit var MimiekGroepeJMMEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page31)
        //
        val entryDescription = "Spraak Junior Groepe"
        vanneNameEditText = findViewById(R.id.editTextText153)
        SkoolEditText = findViewById(R.id.editTextText151)
        GraadEditText = findViewById(R.id.editTextText148)
        EkspProgramJEPEditText = findViewById(R.id.editTextText150)
        GroepLeesJVLEditText = findViewById(R.id.editTextText154)
        KoorspraakJKSEditText = findViewById(R.id.editTextText152)
        MimiekGroepeJMMEditText = findViewById(R.id.editTextText149)
        FaceToFaceCheckBox = findViewById(R.id.checkBox53)
        VideoCheckBox = findViewById(R.id.checkBox54)
        val button: Button = findViewById(R.id.button26)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val Skool = SkoolEditText.text.toString()
            val Graad = GraadEditText.text.toString()
            val EkspProgramJEP = EkspProgramJEPEditText.text.toString()
            val GroepLeesJVL = GroepLeesJVLEditText.text.toString()
            val KoorspraakJKS = KoorspraakJKSEditText.text.toString()
            val MimiekGroepeJMM = MimiekGroepeJMMEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, Skool, Graad, EkspProgramJEP, GroepLeesJVL, KoorspraakJKS, MimiekGroepeJMM, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName,  Skool, Graad, EkspProgramJEP, GroepLeesJVL, KoorspraakJKS, MimiekGroepeJMM, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, Skool: String, Graad: String, EkspProgramJEP: String, GroepLeesJVL: String, KoorspraakJKS: String, MimiekGroepeJMM: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val Skool: String,
            val Graad: String,
            val EkspProgramJEP: String,
            val GroepLeesJVL: String,
            val KoorspraakJKS: String,
            val MimiekGroepeJMM: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            Skool,
            Graad,
            EkspProgramJEP,
            GroepLeesJVL,
            KoorspraakJKS,
            MimiekGroepeJMM,
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
        Toast.makeText(this@Page31, message, Toast.LENGTH_SHORT).show()
    }
}