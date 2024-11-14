package com.example.adminpanel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adpater.AllItemAdpater
import com.example.adminpanel.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Using immutable lists directly as RecyclerView adapter can handle them
        val menuFoodNames = listOf("Burger", "Pizza", "Tea", "Panipuri")
        val menuItemPrices = listOf("#5", "#10", "#3", "#2")
        val menuItemImages = listOf(R.drawable.th1, R.drawable.th1, R.drawable.th3, R.drawable.th1)

        // Initialize the adapter and pass in the lists
        val adapter =AllItemAdpater(ArrayList(menuFoodNames), ArrayList(menuItemPrices), ArrayList(menuItemImages))

        // Set up RecyclerView with a LinearLayoutManager
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter
    }
}
