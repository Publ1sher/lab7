package com.example.lab7

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private val credentialSharedPref = "our_shared_pref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edUsername = findViewById(R.id.ed_username)
        edPassword = findViewById(R.id.ed_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_signup)

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val inputUsername = edUsername.text.toString()
            val inputPassword = edPassword.text.toString()

            val prefs = getSharedPreferences(credentialSharedPref, Context.MODE_PRIVATE)

            // Получаем пароль
            val savedPassword = prefs.getString(inputUsername, null)

            if (savedPassword != null && savedPassword == inputPassword) {
                // Успешный вход
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val prefs = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
                prefs.edit().putBoolean("IS_LOGGED_IN", true).apply()

                val intent = Intent(this, ProfileActivity::class.java).apply {
                    putExtra("USERNAME", inputUsername)
                }
                startActivity(intent)
                finish()
            } else {

                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}