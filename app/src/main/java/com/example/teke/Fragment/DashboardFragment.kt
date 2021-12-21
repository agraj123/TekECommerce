package com.example.teke.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.teke.Adapter.ProductAdapterSecond
import com.example.teke.Adapter.ProductAdapterThird
import com.example.teke.Product.ProductEntity
import com.example.teke.User.RegisterDatabase
import com.example.teke.User.RegisterEntity
import com.example.teke.databinding.FragmentDashboardBinding
import java.io.ByteArrayOutputStream
import com.example.teke.Adapter.ViewPagerAdapter
import com.example.teke.R

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    lateinit var database: RegisterDatabase

    lateinit var adapterSecond: ProductAdapterSecond
    lateinit var adapterThird: ProductAdapterThird

    lateinit var toggle: ActionBarDrawerToggle

    private val PREF_DATA = "user_room"
    private lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private var currentId: Int = 0
    private var currentName: String = ""
    var currentEmail: String = ""
    var cartQty = 0
    var mViewPager: ViewPager? = null

    var images = intArrayOf(
        R.drawable.ac, R.drawable.tv, R.drawable.headphone, R.drawable.cable,
        R.drawable.laptop, R.drawable.cordless
    )

    var mViewPagerAdapter: ViewPagerAdapter? = null

    @SuppressLint("CommitPrefEdits", "UseRequireInsteadOfGet", "RtlHardcoded", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDashboardBinding.inflate(LayoutInflater.from(context), container, false)

        mViewPagerAdapter = ViewPagerAdapter(requireContext(), images)

        binding.VIEWPAGER.adapter = mViewPagerAdapter

        // Design 1


        sp = this.requireActivity()
            .getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE)
        editor = sp.edit()

        val user = RegisterEntity(0, "", sp.getString("email", "").toString(), "", "", "")

        currentName = sp.getString("name", "").toString()
        currentEmail = sp.getString("email", "").toString()

        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet", "SetTextI18n")
    override fun onResume() {

        //Design1
        database = RegisterDatabase.getInstance(requireContext())
        val b = database.ProductDao().all()
        Log.d("Receive", "onCreateView: $b")
        adapterSecond = ProductAdapterSecond(b, requireContext())
        binding.mainRecycler.adapter = adapterSecond
        binding.mainRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        // Design 2
        val c = database.ProductDao().all()
        Log.d("All", "onCreateView: $c")
        adapterThird = ProductAdapterThird(c, requireContext())
        binding.secondRecycler.adapter = adapterThird
        binding.secondRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false)

        val first = sp.getBoolean("first", true)

        if (first) {
            var product = ProductEntity(
                0,
                convertImage(AppCompatResources.getDrawable(requireContext(), R.drawable.ac)),
                "Ac",
                "28000",
                "LG 3 Start, 1.5 Ton AC",
                "AC",
                currentId,
                0, 0, "", 0, 0
            )
            database.ProductDao().insertProduct(product)
            product = ProductEntity(
                0,
                convertImage(AppCompatResources.getDrawable(requireContext(), R.drawable.cable)),
                "Cable",
                "300",
                "Connector Wire",
                "Wire",
                currentId,
                0, 0, "", 0, 0
            )
            database.ProductDao().insertProduct(product)
            product = ProductEntity(
                0,
                convertImage(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.headphone
                    )
                ),
                "Headphone",
                "2500",
                "Bass, Good Quality, Water Proof",
                "Headphone",
                currentId,
                0, 0, "", 0, 0
            )
            database.ProductDao().insertProduct(product)
            product = ProductEntity(
                0,
                convertImage(AppCompatResources.getDrawable(requireContext(), R.drawable.tv)),
                "TV",
                "22000",
                "4K LED TV",
                "TV",
                currentId,
                0, 0, "", 0, 0
            )
            database.ProductDao().insertProduct(product)
            product = ProductEntity(
                0,
                convertImage(AppCompatResources.getDrawable(requireContext(), R.drawable.laptop)),
                "Laptop",
                "66000",
                "2GB RAM, 4GB Graphics",
                "Laptop",
                currentId,
                0, 0, "", 0, 0
            )
            database.ProductDao().insertProduct(product)
            product = ProductEntity(
                0,
                convertImage(AppCompatResources.getDrawable(requireContext(), R.drawable.mic)),
                "Mic",
                "800",
                "Good sound Quality",
                "Mic",
                currentId,
                0, 0, "", 0, 0
            )
            database.ProductDao().insertProduct(product)
            editor.putBoolean("first", false)
            editor.commit()
        }
        super.onResume()
    }

    private fun convertImage(drawable: Drawable?): ByteArray? {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP, 25, stream)
        return stream.toByteArray()
    }
}