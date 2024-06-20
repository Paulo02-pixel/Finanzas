package com.example.finanzas.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finanzas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivitySignup : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val buttonsignup1 = findViewById<Button>(R.id.button_signup1)
        val imageView = findViewById<ImageView>(R.id.imageCrear)
        val txtnombre1 = findViewById<TextView>(R.id.editTextName1)
        val txtemail1 = findViewById<TextView>(R.id.editTextEmail1)
        val txtpassword1 = findViewById<TextView>(R.id.editTextPassword1)
        val txtrepassword1 = findViewById<TextView>(R.id.editTextRePassword1)

        imageView.setOnClickListener()
        {
            var pass1 = txtpassword1.text.toString()
            var repass1 = txtrepassword1.text.toString()
            if (pass1.equals(repass1))
            {
                createAccount(txtnombre1.text.toString(), txtemail1.text.toString(), txtpassword1.text.toString())
            }
            else
            {
                Toast.makeText(baseContext, "Error: los passwords no coinciden", Toast.LENGTH_SHORT).show()
                txtpassword1.requestFocus()
            }
        }
        firebaseAuth = Firebase.auth

        buttonsignup1.setOnClickListener {
            val intent = Intent(this, MainActivityLogin::class.java)
            intent.putExtra("nombre", txtnombre1.text.toString())
            startActivity(intent)
        }
    }

    private fun createAccount(nombre: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    user?.updateProfile(
                        UserProfileChangeRequest.Builder()
                        .setDisplayName(nombre)
                        .build())
                        ?.addOnCompleteListener { userProfileTask ->
                            if (userProfileTask.isSuccessful) {
                                Toast.makeText(baseContext, "Cuenta Creada Correctamente, $nombre", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivityLogin::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(baseContext, "Algo Salió mal al actualizar el perfil", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(baseContext, "Algo Salió mal, Error: " + task.exception, Toast.LENGTH_SHORT).show()
                }
            }
    }
}