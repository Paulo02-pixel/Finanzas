package com.example.finanzas.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finanzas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivityProfile : AppCompatActivity(){
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseAuth = Firebase.auth
        val currentUser = firebaseAuth.currentUser

        val editTextNameProfile = findViewById<EditText>(R.id.editTextNameProfile)
        val editTextEmailProfile = findViewById<TextView>(R.id.editTextEmailProfile)
        val editTextPasswordProfile = findViewById<EditText>(R.id.editTextPasswordProfile)
        val buttonUpdateProfile = findViewById<Button>(R.id.buttonUpdateProfile)
        val buttonUpdatePassword = findViewById<Button>(R.id.buttonUpdatePassword)
        val buttonSignOut = findViewById<Button>(R.id.buttonSignOut)
        val buttonMain = findViewById<ImageButton>(R.id.buttonMain)

        currentUser?.let {
            editTextNameProfile.setText(it.displayName)
            editTextEmailProfile.setText(it.email)
        }

        buttonUpdateProfile.setOnClickListener {
            val updatedName = editTextNameProfile.text.toString()
            if (updatedName.isNotEmpty()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(updatedName)
                    .build()
                currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Nombre actualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al actualizar nombre", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
        buttonUpdatePassword.setOnClickListener {
            val newPassword = editTextPasswordProfile.text.toString()
            if (newPassword.isNotEmpty()) {
                currentUser?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al actualizar contraseña", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }
        buttonSignOut.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, MainActivityIntro::class.java)
            startActivity(intent)
            finish()
        }
        buttonMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}