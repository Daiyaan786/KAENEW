package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page19 : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.burgermenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_profile -> startActivity(Intent(this, UserProfile::class.java))
            R.id.entries -> startActivity(Intent(this, EntryForm::class.java))
            R.id.video_entries -> startActivity(Intent(this, VideoEntries::class.java))
            // Add more cases for other menu items if needed

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var KlasEditText: EditText
    private lateinit var SkoolGraadEditText: EditText
    private lateinit var OnderwerpEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page19)
        //
        val entryDescription = "Kuns"
        vanneNameEditText = findViewById(R.id.editTextText79)
        KlasEditText = findViewById(R.id.editTextText80)
        SkoolGraadEditText = findViewById(R.id.editTextText81)
        OnderwerpEditText = findViewById(R.id.editTextText82)
        val button: Button = findViewById(R.id.button12)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val Klas = KlasEditText.text.toString()
            val SkoolGraad = SkoolGraadEditText.text.toString()
            val Onderwerp = OnderwerpEditText.text.toString()
            //
            submitDataToFirebase(entryDescription, vanneEnName, Klas, SkoolGraad, Onderwerp)
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, Klas: String, SkoolGraad: String, Onderwerp: String) {
        data class DataClass(
            val vanneEnName: String,
            val Klas: String,
            val SkoolGraad: String,
            val Onderwerp: String
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            Klas,
            SkoolGraad,
            Onderwerp
        )
        FirebaseUtils.writeChildToTeacher(vanneEnName)

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
        Toast.makeText(this@Page19, message, Toast.LENGTH_SHORT).show()
    }
}