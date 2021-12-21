package com.example.teke.Fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.teke.User.RegisterDatabase
import com.example.teke.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs<DetailFragmentArgs>()
    lateinit var database: RegisterDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Detail screen"
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        database = RegisterDatabase.getInstance(requireContext())

        val wishListData = args.wishdata
        binding.detailName.text = wishListData

        val second = args.name
        binding.detailName.text = second

        val name = args.data
        val nameList = database.ProductDao().getName(name)
        binding.detailIv.setImageBitmap(getImage(nameList[0].product_image))
        binding.detailName.text = nameList[0].product_name
        binding.detailAmount.text = nameList[0].product_amount
        binding.detailCategory.text = nameList[0].product_category
        binding.detailDescription.text = nameList[0].product_description

        return binding.root
    }

    private fun getImage(byteArray: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
    }
}