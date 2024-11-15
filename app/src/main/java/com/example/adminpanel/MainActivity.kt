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
            val intent= Intent(this,Add_item2::class.java)
            startActivity(intent)
        }
        binding.allitemMenu.setOnClickListener {
            val intent= Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.outFordelivery.setOnClickListener {
            val intent= Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.profilebutton.setOnClickListener {
            val intent= Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.CrateUser.setOnClickListener {
            val intent= Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.PendingodertextView.setOnClickListener {
            val intent= Intent(this,PendingorderActivity::class.java)
            startActivity(intent)
        }
    }
}
