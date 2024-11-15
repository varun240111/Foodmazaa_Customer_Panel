package com.example.adminpanel

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.databinding.ActivityLoginLoginBinding

class login_login : AppCompatActivity() {
    private val binding:ActivityLoginLoginBinding by lazy{
        ActivityLoginLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.loginButton.setOnClickListener{
            val intent= Intent(this,sighup2::class.java)
            startActivity(intent)
        }
        binding.noAccount.setOnClickListener{
            val intent= Intent(this,sighup2::class.java)
            startActivity(intent)
        }

    }
}