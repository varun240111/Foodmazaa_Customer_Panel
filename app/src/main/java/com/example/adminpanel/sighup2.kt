package com.example.adminpanel

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.databinding.ActivitySighup2Binding

class sighup2 : AppCompatActivity() {
    private val binding:ActivitySighup2Binding by lazy {
        ActivitySighup2Binding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.Createbutton.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.Createbutton.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.alreadyAccountTextView.setOnClickListener{
            val intent= Intent(this,login_login::class.java)
            startActivity(intent)
        }
        val locationlist = arrayOf("Yeola", "Kopergaon", "Shirdi", "Nashik")
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, locationlist)
        val autoCompleteTextView = binding.ListOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }
}