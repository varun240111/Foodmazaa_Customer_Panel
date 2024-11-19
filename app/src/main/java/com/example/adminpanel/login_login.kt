package com.example.adminpanel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.adminpanel.databinding.ActivityLoginLoginBinding
import com.example.adminpanel.model.UserModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class login_login : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginLoginBinding by lazy {
        ActivityLoginLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase and Facebook SDK
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        auth = Firebase.auth
        database = Firebase.database.reference

        setupFacebookLogin()
        setupGoogleSignIn()

        // Email/Password Login
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            } else {
                loginOrCreateAccount(email, password)
            }
        }

        // Navigate to Signup
        binding.noAccount.setOnClickListener {
            startActivity(Intent(this, signup2::class.java)) // Ensure SignupActivity exists
        }

        // Google Login
        binding.googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private fun setupFacebookLogin() {
        val facebookLoginButton = findViewById<AppCompatButton>(R.id.facebookButton)
        facebookLoginButton.setOnClickListener {
            // Use the LoginManager directly for login flow
            val loginManager = com.facebook.login.LoginManager.getInstance()
            loginManager.logInWithReadPermissions(this, listOf("email", "public_profile"))
            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val token = result.accessToken
                    val credential = FacebookAuthProvider.getCredential(token.token)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@login_login, "Facebook sign in successful", Toast.LENGTH_SHORT).show()
                                updateUI(auth.currentUser)
                            } else {
                                showError("Facebook sign in failed", task.exception)
                            }
                        }
                }

                override fun onCancel() {
                    Toast.makeText(this@login_login, "Facebook sign in canceled", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    showError("Facebook sign in error", error)
                }
            })
        }
    }


    private fun setupGoogleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            task.addOnSuccessListener { account ->
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Google sign in successful", Toast.LENGTH_SHORT).show()
                            updateUI(auth.currentUser)
                        } else {
                            showError("Google sign in failed", it.exception)
                        }
                    }
            }.addOnFailureListener { error ->
                showError("Google sign in error", error)
            }
        }
    }

    private fun loginOrCreateAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    updateUI(auth.currentUser)
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { createTask ->
                            if (createTask.isSuccessful) {
                                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                                saveUserData()
                                updateUI(auth.currentUser)
                            } else {
                                showError("Account creation failed", createTask.exception)
                            }
                        }
                }
            }
    }

    private fun saveUserData() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val user = UserModel(email, password)

        auth.currentUser?.uid?.let { userId ->
            database.child("users").child(userId).setValue(user)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            Log.d("UserInfo", "User logged in: ${it.email}")
        }
        navigateToMainActivity()
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError(message: String, error: Throwable?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.e("LoginActivity", "$message: ${error?.localizedMessage}", error)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
