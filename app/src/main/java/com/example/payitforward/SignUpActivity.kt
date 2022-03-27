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
import com.example.payitforward.pojo.User
import com.example.payitforward.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    var signInButton: Button? = null
    var signUpButton: Button? = null
    var signUpEmail: EditText? = null
    var signUpPassword: EditText? = null
    var signUpRepeatedPassword: EditText? = null

    private val auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.sign_up)
        signInButton = findViewById<View>(R.id.signin) as Button
        signInButton!!.setOnClickListener { signInActivity() }

        signUpButton = findViewById<View>(R.id.signup_button) as Button
        signUpButton!!.setOnClickListener { signUpActivity() }

    }

    private fun signUpActivity() {
        signUpEmail = findViewById<View>(R.id.login) as EditText
        signUpPassword = findViewById<View>(R.id.password) as EditText
        signUpRepeatedPassword = findViewById<View>(R.id.password_repeat) as EditText

        val email = signUpEmail!!.text.toString()
        val password = signUpPassword!!.text.toString()
        val repeatedPassword = signUpRepeatedPassword!!.text.toString()


        if (password == repeatedPassword) {
            createUserWithPassword(email, password)
        }
    }

    private fun createUserWithPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        FirestoreUtil.addUser(
                            User(
                                user.uid,
                                "",
                                getUsername(email),
                                email,
                                "",
                                ""
                            )
                        )
                        Log.d(TAG, "createUserWithEmailAndPassword with UUID ${user.uid}:success")
                    }
                    runActivity(MenuActivity::class.java)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed. Please try another password.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun signInActivity() {
        runActivity(MainActivity::class.java)
    }

    private fun runActivity(loadClass: Class<*>) {
        val intent = Intent(this, loadClass)
        startActivity(intent)
    }

    private fun getUsername(email: String): String {
        val i = email.indexOf("@")
        return email.substring(0, i)
    }
}