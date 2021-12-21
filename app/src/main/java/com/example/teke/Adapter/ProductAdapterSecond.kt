package com.example.teke.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import com.example.teke.Fragment.DashboardFragmentDirections
import com.example.teke.Product.ProductEntity
import com.example.teke.R

class ProductAdapterSecond(recentEntityEntity: List<ProductEntity?>, var context: Context) :
    RecyclerView.Adapter<ProductAdapterSecond.ViewHolder>() {
    private val recentEntityEntity: List<ProductEntity?>? by lazy { recentEntityEntity }
    var arrayList = recentEntityEntity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.dashboarddesign, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(getImage(arrayList[position]!!.product_image))
        holder.name.text = arrayList[position]!!.product_name

        holder.itemView.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(holder.name.text.toString())

            Navigation.findNavController(holder.itemView)
                .navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return recentEntityEntity!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.textSampleDesign)
        var image: ImageView = itemView.findViewById(R.id.firstImageDesign)
    }

    private fun getImage(byteArray: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
    }

    class ProductComparator : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.product_image.contentEquals(newItem.product_image)
        }
    }
}