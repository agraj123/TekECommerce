package com.example.teke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teke.Fragment.PlacedOrderFragment
import com.example.teke.databinding.ActivityOrderedProductBinding
import com.example.teke.databinding.FragmentPlacedOrderBinding

class OrderedProduct : AppCompatActivity() {

    lateinit var binding: ActivityOrderedProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderedProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.add(R.id.FragmentContainerView, PlacedOrderFragment())
        transaction.commit()
    }
}