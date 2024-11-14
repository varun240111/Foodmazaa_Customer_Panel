package com.example.adminpanel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addmenu.setOnClickListener {
            val intent= Intent(this,add_item::class.java)
            startActivity(intent)
        }
        binding.allitemMenu.setOnClickListener {
            val intent= Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }

    }
}
