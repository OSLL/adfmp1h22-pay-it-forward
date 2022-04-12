package com.example.payitforward

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private var signUpButton: Button? = null
    private var signInButton: Button? = null
    private var googleSignInButton: LinearLayout? = null

    var signInEmail: EditText? = null
    var signInPassword: EditText? = null

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContentView(R.layout.activity_main)
        signUpButton = findViewById<View>(R.id.signup) as Button
        signUpButton!!.setOnClickListener { signUpActivity() }

        signInButton = findViewById<View>(R.id.login_button) as Button
        signInButton!!.setOnClickListener { signInActivity() }

        googleSignInButton = findViewById<View>(R.id.google_sign_in_button) as LinearLayout
        googleSignInButton!!.setOnClickListener { signInWithGoogle() }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val exception = task.exception
                if (task.isSuccessful) {
                    try {
                        val user = auth.currentUser
                        Log.w(StateSet.TAG, user!!.email.toString())
                        runActivity(MenuActivity::class.java)
                    } catch (e: ApiException) {
                        Log.w(StateSet.TAG, "Google sign in failed", e)
                    }
                } else {
                    Log.w(StateSet.TAG, exception.toString())
                }
            }
        }

    private fun signUpActivity() {
        runActivity(SignUpActivity::class.java)
    }

    private fun signInActivity() {
        signInEmail = findViewById<View>(R.id.login) as EditText
        val email = signInEmail!!.text.toString()

        signInPassword = findViewById<View>(R.id.password) as EditText
        val password = signInPassword!!.text.toString()

        if (email == "" || password == "") {
            Toast.makeText(
                this@MainActivity, "The email and password should be not empty!",
                Toast.LENGTH_LONG
            ).show();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        runActivity(MenuActivity::class.java)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

    private fun runActivity(loadClass: Class<*>) {
        val intent = Intent(this, loadClass)
        startActivity(intent)
        finish()
    }
}