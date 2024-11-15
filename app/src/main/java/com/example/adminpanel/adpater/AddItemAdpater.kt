package com.example.adminpanel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.ItemItem2Binding

class AddItemAdapter(
    private val menuItemNames: MutableList<String>,
    private val menuItemPrices: MutableList<String>,
    private val menuItemImages: MutableList<Int>
) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuItemNames.size) { 1 } // Initialize quantity to 1 for each item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int = menuItemNames.size

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class AddItemViewHolder(private val binding: ItemItem2Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                customername.text = menuItemNames[position]
                priceTextView.text = menuItemPrices[position]
                orderfoodImage.setImageResource(menuItemImages[position])
                quantityTextView.text = itemQuantities[position].toString()

                // Set up button click listeners
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
            menuItemNames.removeAt(position)
            menuItemPrices.removeAt(position)
            menuItemImages.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuItemNames.size)
        }
    }
}
