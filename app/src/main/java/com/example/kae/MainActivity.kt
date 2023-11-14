package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kae.R.layout.mainpage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainpage)

        val buttonLanguage = findViewById<Button>(R.id.buttonLanguage)
        val buttonAbout = findViewById<Button>(R.id.buttonAbout)
        val button3 = findViewById<Button>(R.id.button3)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonLanguage.setOnClickListener {
            // Handle the Language button click here
        }

        buttonAbout.setOnClickListener {
            // Handle the About button click here
            val aboutIntent = Intent(this, About::class.java)
            startActivity(aboutIntent)
        }

        button3.setOnClickListener {
            // Handle the Login button click here
            val burgerMenuIntent = Intent(this, Burgermenu::class.java)
            startActivity(burgerMenuIntent)
        }

        buttonRegister.setOnClickListener {
            // Handle the Register button click here
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}


