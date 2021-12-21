package com.example.teke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teke.Adapter.CartAdapter
import com.example.teke.Fragment.PlacedOrderFragment
import com.example.teke.Product.CartRepo
import com.example.teke.Product.ProductEntity
import com.example.teke.User.RegisterDatabase
import com.example.teke.ViewModel.CartViewModel
import com.example.teke.databinding.ActivityCartBinding
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    lateinit var database: RegisterDatabase
    lateinit var arrayList: List<ProductEntity>
    lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = RegisterDatabase.getInstance(this)

        val ViewModel: CartViewModel by viewModels {
            CartViewModel.viewModelFactory((application as shopApplication).repository)
        }

        ViewModel.allProducts.observe(this) {
            it.let {
                arrayList = it
                adapter = CartAdapter(it, ViewModel, this)
                binding.cartRecycler.setHasFixedSize(true)
                binding.cartRecycler.layoutManager = LinearLayoutManager(this)
                binding.cartRecycler.adapter = adapter
                adapter.submitLIst(it)

                var total = 0
                for (i in it.indices) {
                    if (it[i].cart_total == "") {

                    } else {
                        total += it[i].cart_total.toInt()
                    }
                }
                binding.cartAmount.text = total.toString()
            }
        }

//        arrayList = database.ProductDao().fetchSave()
//        playAdapter =
//            listOf(ProductEntity(0, null, "", "", "", "", 0, 0, 0, "", 0, 0))

//        val a = database.ProductDao().setFavData(1)
//        Log.d("Cart", "onCreateView: $a[0]")
//        adapter = CartAdapter(arrayList, this)
//        binding.cartRecycler.setHasFixedSize(true)
//        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
//        binding.cartRecycler.adapter = adapter

        binding.backCart.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }

        binding.cartPlaceOrderBtn.setOnClickListener {

            for (i in arrayList.indices) {
                val orderData = arrayList[i].product_placed
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
                        1,
                        arrayList[i].cart_order
                    )
                    database.ProductDao().ucart(updateCartData)
                    Toast.makeText(this, "Placed", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, OrderedProduct::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}