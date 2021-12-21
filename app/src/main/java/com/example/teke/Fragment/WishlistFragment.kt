package com.example.teke.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teke.Adapter.ProductAdapterThird
import com.example.teke.Adapter.WishlistAdapter
import com.example.teke.Product.ProductEntity
import com.example.teke.R
import com.example.teke.User.RegisterDatabase
import com.example.teke.ViewModel.CartViewModel
import com.example.teke.databinding.ActivityCartBinding
import com.example.teke.databinding.FragmentWishlistBinding
import com.example.teke.shopApplication
import kotlinx.android.synthetic.main.activity_splash_screen.*

class WishlistFragment : Fragment() {

    lateinit var binding: FragmentWishlistBinding
    lateinit var database: RegisterDatabase
    lateinit var arrayList: List<ProductEntity>
    lateinit var adapter: WishlistAdapter
    private lateinit var playAdapter: List<ProductEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWishlistBinding.inflate(layoutInflater, container, false)

        val ViewModel: CartViewModel by viewModels {
            CartViewModel.viewModelFactory((requireActivity().application as shopApplication).repository)
        }

        database = RegisterDatabase.getInstance(requireContext())
        playAdapter = listOf(ProductEntity(0, null, "", "", "", "", 0, 0, 0, "", 0, 0))

        playAdapter.apply {
            ViewModel.wishList.observe(this@WishlistFragment, Observer {
                arrayList = it
                adapter = WishlistAdapter(arrayList, ViewModel, requireContext())
                binding.wishlistRecycler.setHasFixedSize(true)
                binding.wishlistRecycler.layoutManager = LinearLayoutManager(requireContext())
                binding.wishlistRecycler.adapter = adapter
            })
        }

        return binding.root
    }

}