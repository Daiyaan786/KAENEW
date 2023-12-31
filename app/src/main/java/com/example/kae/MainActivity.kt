package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kae.R.id.btnMainPageLoginBtn
import com.example.kae.R.id.buttonAbout
import com.example.kae.R.id.buttonRegister
import com.example.kae.R.layout.burgermenu
import com.example.kae.R.layout.mainpage


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainpage)


        // Handle navigation to the login and registration activities here
        val loginButton = findViewById<View>(btnMainPageLoginBtn)
        val registerButton = findViewById<View>(buttonRegister)
        val aboutButton = findViewById<View>(buttonAbout)
//LOGIN BUTTON
         loginButton.setOnClickListener {
             val intent = Intent(this@MainActivity, LoginActivity::class.java)
             startActivity(intent) }
//REGISTER BUTTON
        registerButton.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
       }
//ABOUT BUTTON
        aboutButton.setOnClickListener {
            val intent = Intent(this@MainActivity, About::class.java)
            startActivity(intent) }
    }
}


