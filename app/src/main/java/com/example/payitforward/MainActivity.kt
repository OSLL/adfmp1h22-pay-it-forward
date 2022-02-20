package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var signUpButton: Button? = null
    var signInButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signUpButton = findViewById<View>(R.id.signup) as Button
        signUpButton!!.setOnClickListener { signUpActivity() }


        signInButton = findViewById<View>(R.id.login_button) as Button
        signInButton!!.setOnClickListener { signInActivity() }
    }

    private fun signUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun signInActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}