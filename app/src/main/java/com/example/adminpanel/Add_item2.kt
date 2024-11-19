package com.example.adminpanel

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.databinding.ActivityAddItem2Binding
import com.example.adminpanel.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Add_item2 : AppCompatActivity() {

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImageUri: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItem2Binding by lazy {
        ActivityAddItem2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Save item
        binding.AddItemButton.setOnClickListener {
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient = binding.ingredient.text.toString().trim()

            if (foodName.isNotEmpty() && foodPrice.isNotEmpty() && foodDescription.isNotEmpty() && foodIngredient.isNotEmpty()) {
                uploadData()
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Select image
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        // Back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Fetch and display item (for demonstration)
        fetchAndDisplayItem()
    }

    private fun uploadData() {
        val menuRef = database.getReference("menu")
        val newItemKey = menuRef.push().key

        if (foodImageUri != null) {
            val encodedImage = encodeImageToBase64(foodImageUri!!)
            val newItem = AllMenu(
                foodName = foodName,
                foodPrice = foodPrice,
                foodDescription = foodDescription,
                foodIngredient = foodIngredient,
                foodImage = encodedImage // Save Base64 string
            )
            newItemKey?.let { key ->
                menuRef.child(key).setValue(newItem)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "Select an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchAndDisplayItem() {
        val menuRef = database.getReference("menu")
        val sampleItemId = "sampleItemId" // Replace with your item's key or fetch dynamically.

        menuRef.child(sampleItemId).get()
            .addOnSuccessListener { snapshot ->
                val allMenuItem = snapshot.getValue(AllMenu::class.java)
                if (allMenuItem != null && allMenuItem.foodImage != null) {
                    decodeAndDisplayImage(allMenuItem.foodImage!!)
                } else {
                    Toast.makeText(this, "No image found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to fetch item", Toast.LENGTH_SHORT).show()
            }
    }

    private fun decodeAndDisplayImage(encodedImage: String) {
        try {
            val decodedBytes = android.util.Base64.decode(encodedImage, android.util.Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            binding.selectedImage.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun encodeImageToBase64(uri: Uri): String? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            if (bytes != null) {
                android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                binding.selectedImage.setImageURI(uri)
                foodImageUri = uri
            }
        }
}
