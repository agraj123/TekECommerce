package com.example.teke.Adapter

import android.widget.LinearLayout

import android.view.ViewGroup
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.teke.R
import java.util.*

class ViewPagerAdapter(context: Context, images: IntArray) :
    PagerAdapter() {
    // Context object
    var context: Context = context

    var images: IntArray = images

    var mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView: View = mLayoutInflater.inflate(R.layout.itemviewpager, container, false)

        val imageView: ImageView = itemView.findViewById(R.id.imageViewMain) as ImageView

        imageView.setImageResource(images[position])

        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}