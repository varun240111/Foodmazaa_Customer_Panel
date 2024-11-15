package com.example.adminpanel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpanel.adapter.AddItemAdapter
import com.example.adminpanel.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        val menuItems = mutableListOf("Burger","Sandwich","Item")
        val menuPrice=mutableListOf("100","200","300")
        val menuImage=mutableListOf(R.drawable.th1,R.drawable.th3,R.drawable.th3)


        val adapter=AddItemAdapter(ArrayList(menuItems),ArrayList(menuPrice),ArrayList(menuImage))
        binding.MenuRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter=adapter

    }
}
