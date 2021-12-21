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
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import com.example.teke.Product.ProductEntity
import com.example.teke.R
import com.example.teke.User.RegisterDatabase
import androidx.navigation.Navigation
import com.example.teke.Fragment.WishlistFragmentDirections
import com.example.teke.ViewModel.CartViewModel

class WishlistAdapter(product: List<ProductEntity?>, private val cartViewModel: CartViewModel, var context: Context) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    private val product: List<ProductEntity?>? by lazy { product }
    var arrayList = product
    private lateinit var database: RegisterDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.wishlistdesign, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(getImage(arrayList[position]!!.product_image))
        holder.name.text = arrayList[position]!!.product_name
        holder.amount.text = arrayList[position]!!.product_amount

        database = RegisterDatabase.getInstance(context)

        val save = arrayList[position]!!.product_save
        Log.d("TAG", "onBindViewHolder: $save")
        database.ProductDao().fetchWishList("", save)

        val cartData = arrayList[position]!!.cart_qty
        Log.d("TAG", "onBindViewHolder: $cartData")

        holder.savetext.setOnClickListener {

            if (cartData == 0) {
                Log.d("position", "onBindViewHolder: $position")
                val updateCartData = ProductEntity(
                    arrayList[position]!!.productId,
                    arrayList[position]!!.product_image,
                    arrayList[position]!!.product_name,
                    arrayList[position]!!.product_amount,
                    arrayList[position]!!.product_description,
                    arrayList[position]!!.product_category,
                    arrayList[position]!!.product_userid,
                    arrayList[position]!!.product_save, 1, arrayList[position]!!.cart_total, 0, 0
                )
                cartViewModel.updateCart(updateCartData)
            } else {
                Toast.makeText(context, "Already in cart", Toast.LENGTH_SHORT).show()
            }
        }
        val wishListData = arrayList[position]!!.product_save
        Log.d("TAG", "onBindViewHolder: $cartData")

        holder.imagecart.setOnClickListener {
            if (wishListData == 1) {
                val updateCartData = ProductEntity(
                    arrayList[position]!!.productId,
                    arrayList[position]!!.product_image,
                    arrayList[position]!!.product_name,
                    arrayList[position]!!.product_amount,
                    arrayList[position]!!.product_description,
                    arrayList[position]!!.product_category,
                    arrayList[position]!!.product_userid,
                    0, arrayList[position]!!.cart_qty, arrayList[position]!!.cart_total, 0, 0
                )
                cartViewModel.updateCart(updateCartData)
                holder.imagecart.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.save_foreground
                    )
                )
            } else {
                holder.imagecart.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.filled_save_foreground
                    )
                )
            }
        }

        holder.itemView.setOnClickListener {
            val action =
                WishlistFragmentDirections.actionWishlistFragmentToDetailFragment2(holder.name.text.toString())

            Navigation.findNavController(holder.itemView)
                .navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return product!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.wishlist_name)
        var amount: TextView = itemView.findViewById(R.id.wishlist_amount)
        var image: ImageView = itemView.findViewById(R.id.wishlist_iv)
        var imagecart: ImageView = itemView.findViewById(R.id.wishlist_save)
        var savetext: TextView = itemView.findViewById(R.id.wishlist_cart)
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