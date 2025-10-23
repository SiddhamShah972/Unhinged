package com.example.mad_unhinged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileSetupActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        val saveButton: Button = findViewById(R.id.btnSave)
        val nameEditText: EditText = findViewById(R.id.etName)
        val bioEditText: EditText = findViewById(R.id.etBio)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val bio = bioEditText.text.toString()

            if (name.isNotEmpty() && bio.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null) {
                    val userProfile = hashMapOf(
                        "name" to name,
                        "bio" to bio,
                        "profileImageUrl" to ""
                    )

                    db.collection("users").document(user.uid)
                        .set(userProfile)
                        .addOnSuccessListener { 
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error saving profile: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}