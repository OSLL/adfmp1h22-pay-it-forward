package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogOutActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (auth.currentUser != null) {
            auth.signOut()
            runActivity(MainActivity::class.java)
        }
    }

    private fun runActivity(loadClass: Class<*>) {
        val intent = Intent(this, loadClass)
        startActivity(intent)
        finish()
    }
}
