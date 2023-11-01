package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kae.R.id.button3
import com.example.kae.R.id.buttonRegister
import com.example.kae.R.layout.burgermenu
import com.example.kae.R.layout.mainpage
import com.example.kae.R.layout.page1
import com.example.kae.R.layout.page13
import com.example.kae.R.layout.register

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(page1)


        // Handle navigation to the login and registration activities here
        //     val loginButton = findViewById<View>(button3)
        //val registerButton = findViewById<View>(buttonRegister)

        /*     loginButton.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    */
    }
}
