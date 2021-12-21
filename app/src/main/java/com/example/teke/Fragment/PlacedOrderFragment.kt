package com.example.teke.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teke.Adapter.MyOrderAdapter
import com.example.teke.OrderedProduct
import com.example.teke.Product.ProductEntity
import com.example.teke.R
import com.example.teke.User.RegisterDatabase
import com.example.teke.databinding.FragmentPlacedOrderBinding

class PlacedOrderFragment : Fragment() {

    lateinit var binding: FragmentPlacedOrderBinding
    lateinit var database: RegisterDatabase
    lateinit var arrayList: List<ProductEntity>
    lateinit var adapter: MyOrderAdapter
    private lateinit var playAdapter: List<ProductEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlacedOrderBinding.inflate(layoutInflater, container, false)

        database = RegisterDatabase.getInstance(requireContext())
        arrayList = database.ProductDao().fetchOrder()
        playAdapter =
            listOf(ProductEntity(0, null, "", "", "", "", 0, 0, 0, "", 0, 0))

        val a = database.ProductDao().setOrderData(1)
        Log.d("Cart", "onCreateView: $a[0]")
        adapter = MyOrderAdapter(arrayList, requireContext())
        binding.recyclerOrderPlaced.setHasFixedSize(true)
        binding.recyclerOrderPlaced.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerOrderPlaced.adapter = adapter

        binding.buyNowBtn.setOnClickListener {

            for (i in arrayList.indices) {
                val orderData = arrayList[i].cart_order
                Log.d("TAG", "onBindViewHolder: $orderData")
                if (orderData == 0) {
                    Log.d("position", "onBindViewHolder: $i")
                    val updateCartData = ProductEntity(
                        arrayList[i].productId,
                        arrayList[i].product_image,
                        arrayList[i].product_name,
                        arrayList[i].product_amount,
                        arrayList[i].product_description,
                        arrayList[i].product_category,
                        arrayList[i].product_userid,
                        arrayList[i].product_save,
                        arrayList[i].cart_qty,
                        arrayList[i].cart_total,
                        0,
                        1
                    )
                    database.ProductDao().ucart(updateCartData)
                } else {
                    Toast.makeText(requireContext(), "Ordered", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), OrderedProduct::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }

            val nextFrag = MyOrderFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainerView, nextFrag)
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

}