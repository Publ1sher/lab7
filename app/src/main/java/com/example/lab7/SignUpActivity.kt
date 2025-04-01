package com.example.lab7

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var btnCreateUser: Button

    private val credentialSharedPref = "our_shared_pref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        edUsername = findViewById(R.id.ed_username)
        edPassword = findViewById(R.id.ed_password)
        edConfirmPassword = findViewById(R.id.ed_confirm_password)
        btnCreateUser = findViewById(R.id.btn_create_user)

        btnCreateUser.setOnClickListener {
            val password = edPassword.text.toString()
            val confirmPassword = edConfirmPassword.text.toString()
            val username = edUsername.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username/password cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences(credentialSharedPref, Context.MODE_PRIVATE)
            val existingUsernames = prefs.getStringSet("usernames", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            if (existingUsernames.contains(username)) {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show()
            } else {
                existingUsernames.add(username)
                prefs.edit().apply {
                    putStringSet("usernames", existingUsernames)
                    putString(username, password)
                    apply()
                }
                Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}