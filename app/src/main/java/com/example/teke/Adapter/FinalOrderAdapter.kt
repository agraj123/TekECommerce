package com.example.teke.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.example.teke.Product.ProductEntity
import com.example.teke.R
import com.example.teke.User.RegisterDatabase
import kotlinx.android.synthetic.main.dashboarddesign2.view.*

class FinalOrderAdapter(productList: List<ProductEntity?>?, context: Context) :
    RecyclerView.Adapter<FinalOrderAdapter.ViewHolder>() {

    private val productList: List<ProductEntity?>? by lazy { productList }
    var arrayList = productList
    var context: Context = context
    private lateinit var database: RegisterDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.finalorderdesign, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(getImage(arrayList!![position]!!.product_image))
        holder.name.text = arrayList!![position]!!.product_name
        holder.qnt.text = arrayList!![position]!!.cart_qty.toString()
        holder.price.text = arrayList!![position]!!.product_amount

        database = RegisterDatabase.getInstance(context)
    }

    override fun getItemCount(): Int {
        return productList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imageOProduct)
        var name: TextView = itemView.findViewById(R.id.textOrder)
        var qnt: TextView = itemView.findViewById(R.id.textQnt)
        var price: TextView = itemView.findViewById(R.id.textPrice)
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
