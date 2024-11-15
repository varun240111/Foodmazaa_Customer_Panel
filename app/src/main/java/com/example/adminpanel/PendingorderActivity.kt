package com.example.adminpanel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapter.PendingorderAdapter
import com.example.adminpanel.databinding.ActivityPendingorderBinding

class PendingorderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingorderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingorderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button click listener to finish the activity
        binding.backButton.setOnClickListener {
            finish()
        }

        // Sample data for RecyclerView
        val orderCustomerNames = arrayListOf("Sushant More", "Varun Pawar", "Pramod Mitake")
        val orderQuantity = arrayListOf("8", "2", "1")
        val orderImages = arrayListOf(R.drawable.th3, R.drawable.th1, R.drawable.th3)

        // Correct adapter class used
        val adapter = PendingorderAdapter(orderCustomerNames, orderQuantity, orderImages, this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
