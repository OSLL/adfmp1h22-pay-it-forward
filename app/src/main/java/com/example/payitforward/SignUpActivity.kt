package com.example.payitforward


import android.app.Activity
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
import androidx.constraintlayout.widget.StateSet.TAG
import com.example.payitforward.pojo.User
import com.example.payitforward.util.FirestoreUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {
    private var signInButton: Button? = null
    private var signUpButton: Button? = null
    private var googleSignUpButton: LinearLayout? = null
    private var signUpEmail: EditText? = null
    private var signUpPassword: EditText? = null
    private var signUpRepeatedPassword: EditText? = null

    private val auth: FirebaseAuth = Firebase.auth
    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton = findViewById<View>(R.id.signin) as Button
        signInButton!!.setOnClickListener { signInActivity() }

        signUpButton = findViewById<View>(R.id.signup_button) as Button
        signUpButton!!.setOnClickListener { signUpActivity() }

        googleSignUpButton = findViewById<View>(R.id.google_sign_up_button) as LinearLayout
        googleSignUpButton!!.setOnClickListener { signUpWithGoogle() }
    }

    private fun signUpWithGoogle() {
        val signInIntent = googleSignInClient!!.signInIntent
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
                        val email: String = user!!.email.toString()
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
                        Log.d(TAG, "signUpWithGoogle with UUID ${user.uid}:success")
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                        firebaseAuthWithGoogle(account.idToken!!)

                    } catch (e: ApiException) {
                        Log.w(TAG, "Google sign in failed", e)
                    }
                } else {
                    Log.w(TAG, exception.toString())
                }
            }
        }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    runActivity(MenuActivity::class.java)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
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
        finish()
    }

    private fun getUsername(email: String): String {
        val i = email.indexOf("@")
        return email.substring(0, i)
    }
}