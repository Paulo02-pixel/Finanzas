package com.example.finanzas.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.finanzas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivityLogin : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = Firebase.auth

        val buttonRegister = findViewById<Button>(R.id.button_register)
        val txtemail = findViewById<TextView>(R.id.editTextEmail)
        val txtpassword = findViewById<TextView>(R.id.editTextPassword)

        buttonRegister.setOnClickListener {
            val intent = Intent(this, MainActivitySignup::class.java)
            startActivity(intent)
        }

        val imageView = findViewById<ImageView>(R.id.imageIngresar)
        imageView.setOnClickListener {
            signIn(txtemail.text.toString(), txtpassword.text.toString())
        }
    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val nombre = user?.displayName
                    Toast.makeText(baseContext, "Autentificación Exitosa, $nombre", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nombre", nombre)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Error de Email y/o Password", Toast.LENGTH_SHORT).show()
                }
            }
    }
}