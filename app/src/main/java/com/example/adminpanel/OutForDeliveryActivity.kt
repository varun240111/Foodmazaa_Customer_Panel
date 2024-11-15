package com.example.adminpanel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adpater.DeliveryAdpater
import com.example.adminpanel.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding:ActivityOutForDeliveryBinding  by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backButton.setOnClickListener(){
            finish()
        }

        val customerNames = arrayListOf("Sushant More", "Varun Pawar", "Pramod Mitake")
        val moneyStatus = arrayListOf("received", "notReceived", "Pending")

        val adpater=DeliveryAdpater(customerNames, moneyStatus)
        binding.deliveryRecyclerView.adapter = adpater
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}