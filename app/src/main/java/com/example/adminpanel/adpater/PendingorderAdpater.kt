package com.example.adminpanel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.PendingOrderItemBinding

class PendingorderAdapter(
    private val customerName: ArrayList<String>,
    private val quantity: ArrayList<String>,
    private val foodImage: ArrayList<Int>,
    private val context: Context
) : RecyclerView.Adapter<PendingorderAdapter.PendingorderViewHolder>() {

    // Track acceptance state for each item
    private val acceptanceState: MutableList<Boolean> = MutableList(customerName.size) { false }

    inner class PendingorderViewHolder(private val binding: PendingOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                customername.text = customerName[position]
                Quatitybutton.text = quantity[position]
                orderfoodImage.setImageResource(foodImage[position])

                // Update button text based on acceptance state
                Accpetbutton.text = if (acceptanceState[position]) "Dispatch" else "Accept"

                Accpetbutton.setOnClickListener {
                    if (!acceptanceState[position]) {
                        acceptanceState[position] = true
                        Accpetbutton.text = "Dispatch"
                        showToast("Order is Acceptable")
                    } else {
                        deleteItem(position)
                        showToast("Order is Dispatched")
                    }
                }
            }
        }

        // Function to show toast message
        private fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Function to delete the item
        private fun deleteItem(position: Int) {
            customerName.removeAt(position)
            quantity.removeAt(position)
            foodImage.removeAt(position)
            acceptanceState.removeAt(position) // Remove corresponding state
            notifyItemRemoved(position) // Refresh the RecyclerView to remove the item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingorderViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingorderViewHolder(binding)
    }

    override fun getItemCount(): Int = customerName.size

    override fun onBindViewHolder(holder: PendingorderViewHolder, position: Int) {
        holder.bind(position)
    }
}
