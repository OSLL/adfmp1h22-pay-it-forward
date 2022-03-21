package com.example.payitforward

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    var signUpButton: Button? = null
    var signInButton: Button? = null

    var signInEmail: EditText? = null
    var signInPassword: EditText? = null

    var auth: FirebaseAuth = Firebase.auth

    val currentUser = auth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (currentUser != null) {
        }

        setContentView(R.layout.activity_main)
        signUpButton = findViewById<View>(R.id.signup) as Button
        signUpButton!!.setOnClickListener { signUpActivity() }


        signInButton = findViewById<View>(R.id.login_button) as Button
        signInButton!!.setOnClickListener { signInActivity() }

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val userId = currentUser?.uid
        if (userId != null) {
            preferences.edit().putString("user_id", userId).apply()
        }
    }

    private fun signUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun signInActivity() {
        signInEmail = findViewById<View>(R.id.login) as EditText
        val email = signInEmail!!.text.toString()

        signInPassword = findViewById<View>(R.id.password) as EditText
        val password = signInPassword!!.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }

    }
}