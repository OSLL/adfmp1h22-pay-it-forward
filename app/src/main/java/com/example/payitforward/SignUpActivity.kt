package com.example.payitforward


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    var signInButton: Button? = null
    var signUpButton: Button? = null

    var signUpEmail: EditText? = null
    var signUpPassword: EditText? = null
    var signUpRepeatedPassword: EditText? = null

    val auth: FirebaseAuth = Firebase.auth
    val currentUser = auth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (currentUser != null) {
        }
        setContentView(R.layout.sign_up)
        signInButton = findViewById<View>(R.id.signin) as Button
        signInButton!!.setOnClickListener { signInActivity() }

        signUpButton = findViewById<View>(R.id.signup_button) as Button
        signUpButton!!.setOnClickListener { signUpActivity() }

    }

    private fun signUpActivity() {
        signUpEmail = findViewById<View>(R.id.login) as EditText
        val email = signUpEmail!!.text.toString()

        signUpPassword = findViewById<View>(R.id.password) as EditText
        val password = signUpPassword!!.text.toString()

        signUpRepeatedPassword = findViewById<View>(R.id.password_repeat) as EditText
        val repeatedPassword = signUpRepeatedPassword!!.text.toString()

        if (password == repeatedPassword) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
//                        updateUI(null)
                    }
                }
        }
//        val intent = Intent(this, MenuActivity::class.java)
//        startActivity(intent)
    }

    private fun signInActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}