package com.example.adminpanel.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.ItemItem2Binding
import com.example.adminpanel.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    private val databaseReference: DatabaseReference
) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size) { 1 } // Initialize quantity to 1 for each item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(menuList[position], position)
    }

    inner class AddItemViewHolder(private val binding: ItemItem2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: AllMenu, position: Int) {
            binding.apply {
                // Display text data
                customername.text = menuItem.foodName
                priceTextView.text = menuItem.foodPrice
                quantityTextView.text = itemQuantities[position].toString()

                // Decode and set the Base64 image
                val base64Image = menuItem.foodImage
                if (!base64Image.isNullOrEmpty()) {
                    try {
                        val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        orderfoodImage.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        orderfoodImage.setImageResource(android.R.color.transparent) // Fallback
                    }
                } else {
                    orderfoodImage.setImageResource(android.R.color.transparent) // Fallback
                }
                // Button actions
                minusButton.setOnClickListener { decreaseQuantity(position) }
                plusButton.setOnClickListener { increaseQuantity(position) }
                Accpetbutton.setOnClickListener { deleteItem(position) }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) { // Allow quantity to go up to 10
                itemQuantities[position]++
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) { // Ensure quantity doesn’t go below 1
                itemQuantities[position]--
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }


        private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }
    }
}
