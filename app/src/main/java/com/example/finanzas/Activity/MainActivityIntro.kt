package com.example.finanzas.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.finanzas.R

class MainActivityIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val buttonlogin = findViewById<Button>(R.id.button_login)

        buttonlogin.setOnClickListener {
            val intent = Intent(this, MainActivityLogin::class.java)
            startActivity(intent)
        }

        val buttonsignup = findViewById<Button>(R.id.button_signup)

        buttonsignup.setOnClickListener {
            val intent = Intent(this, MainActivitySignup::class.java)
            startActivity(intent)
        }
    }
}