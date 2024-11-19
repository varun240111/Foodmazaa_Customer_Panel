package com.example.adminpanel

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.databinding.ActivitySighup2Binding
import com.example.adminpanel.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class signup2 : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var nameOfStore: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val binding: ActivitySighup2Binding by lazy {
        ActivitySighup2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initialize Firebase Auth
        auth = Firebase.auth
        //initialize Firebase database
        database = Firebase.database.reference


        binding.createbutton.setOnClickListener {
            //get text from edit text
            userName = binding.name.text.toString().trim()
            nameOfStore = binding.nameofresturent.text.toString().trim()
            email = binding.Email1.text.toString().trim()
            password = binding.password.text.toString().trim()

            if(userName.isBlank() || nameOfStore.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
            else{
                createAccount(email,password)
            }

        }
        binding.alreadyAccountTextView.setOnClickListener {
            val intent = Intent(this,login_login::class.java)
            startActivity(intent)
        }
        val locationlist = arrayOf("Nashik", "Ahmednagar", "Yeola", "Kopargaon")
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, locationlist)
        val autoCompleteTextView = binding.ListOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Account created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this,login_login::class.java)
                startActivity(intent)
                finish()
            }

            else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure",task.exception)
            }
        }
    }

    //save data into database
    private fun saveUserData() {
        //get text from edit text
        userName = binding.name.text.toString().trim()
        nameOfStore = binding.subtitleTextView.text.toString().trim()
        email = binding.Email1.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = UserModel(userName,nameOfStore,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        //save user data into firebase database
        database.child("user").child(userId).setValue(user)
    }
}
