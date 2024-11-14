package com.example.adminpanel.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.databinding.ItemItem2Binding

class AllItemAdpater(private val MenuItemName: MutableList<String>, private val MenuItemPrice: MutableList<String>, private val MenuItemImage: MutableList<Int>) : RecyclerView.Adapter<AllItemAdpater.AddItemViewHolder>() {

    private val itemQutites=IntArray(MenuItemName.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=ItemItem2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int =MenuItemName.size
    inner class AddItemViewHolder(val binding: ItemItem2Binding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                productNameTextView.text = MenuItemName[position]
                priceTextView.text = MenuItemPrice[position]
                foodImageView.setImageResource(MenuItemImage[position])

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                    deleteQuantity(position)
                    increaseQuantity(position)
                }

            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQutites[position]<10){
                itemQutites[position]++
                binding.quantityTextView.text=itemQutites[position].toString()
            }

        }

        private fun decreaseQuantity(position: Int) {
            if (itemQutites[position]<1){
                itemQutites[position]--
                binding.quantityTextView.text=itemQutites[position].toString()
            }
        }
        private fun deleteQuantity(position: Int) {
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,MenuItemName.size)
        }
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.binding.apply {

        }
    }

}