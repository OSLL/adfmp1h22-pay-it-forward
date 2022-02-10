package com.example.payitforward


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class SignUpActivity : AppCompatActivity() {
    var signInButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        signInButton = findViewById<View>(R.id.signin) as Button
        signInButton!!.setOnClickListener { signInActivity() }
    }

    private fun signInActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}