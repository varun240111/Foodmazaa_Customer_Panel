package com.example.adminpanel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminpanel.databinding.ActivityAddItem2Binding


class Add_item2 : AppCompatActivity() {
    private val binding: ActivityAddItem2Binding by lazy {
        ActivityAddItem2Binding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.selectedImage.setOnClickListener{
            pickimage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.backButton.setOnClickListener{
            finish()
        }
    }
    val pickimage=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
        if(uri !=null)
        {
            binding.selectedImage.setImageURI(uri)
        }
    }
}