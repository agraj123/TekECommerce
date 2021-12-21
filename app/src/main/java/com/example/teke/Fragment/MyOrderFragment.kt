package com.example.teke.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teke.Adapter.CartAdapter
import com.example.teke.Adapter.FinalOrderAdapter
import com.example.teke.Adapter.MyOrderAdapter
import com.example.teke.Product.ProductEntity
import com.example.teke.R
import com.example.teke.User.RegisterDatabase
import com.example.teke.databinding.FragmentMyOrderBinding

class MyOrderFragment : Fragment() {

    lateinit var binding: FragmentMyOrderBinding
    lateinit var database: RegisterDatabase
    lateinit var arrayList: List<ProductEntity>
    lateinit var adapter: FinalOrderAdapter
    private lateinit var playAdapter: List<ProductEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyOrderBinding.inflate(layoutInflater, container, false)

        database = RegisterDatabase.getInstance(requireContext())
        arrayList = database.ProductDao().fetchFinalOrder()

//        playAdapter =
//            listOf(ProductEntity(0, null, "", "", "", "", 0, 0, 0, "", 0, 0))

        val a = database.ProductDao().setFinalOrderData(1)
        Log.d("Cart", "onCreateView: $a[0]")
        adapter = FinalOrderAdapter(arrayList, requireContext())
        binding.recyclerOrder.setHasFixedSize(true)
        binding.recyclerOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerOrder.adapter = adapter

        return binding.root
    }
}