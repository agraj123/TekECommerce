package com.example.teke.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.teke.ViewModel.CartViewModel

class CartAdapter(
    productList: List<ProductEntity?>?,
    private val cartViewModel: CartViewModel,
    context: Context
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val productList: List<ProductEntity?>? = productList
    var context: Context = context
    var arrayList = productList
    lateinit var database: RegisterDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cartdesign, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(getImage(arrayList!![position]!!.product_image))
        holder.name.text = arrayList!![position]!!.product_name
        holder.amount.text = arrayList!![position]!!.product_amount
        holder.total.text = arrayList!![position]!!.cart_total
        holder.cartQty.text = arrayList!![position]!!.cart_qty.toString()

        database = RegisterDatabase.getInstance(context)

        holder.cartPlus.setOnClickListener {
//            Toast.makeText(context, "${arrayList!![position]?.cart_qty}", Toast.LENGTH_SHORT)
//                .show()
            val id = arrayList!![position]!!.productId
            var qty = arrayList!![position]!!.cart_qty
            var amount = arrayList!![position]!!.product_amount
            qty += 1
            amount = (qty * amount.toInt()).toString()
            cartViewModel.updateCart(
                ProductEntity(
                    id,
                    arrayList!![position]!!.product_image,
                    arrayList!![position]!!.product_name,
                    arrayList!![position]!!.product_amount,
                    arrayList!![position]!!.product_description,
                    arrayList!![position]!!.product_category,
                    arrayList!![position]!!.product_userid,
                    arrayList!![position]!!.product_save, qty, amount, 0, 0
                )
            )
        }

        holder.cartMinus.setOnClickListener {

            val id = arrayList!![position]!!.productId
            var qty = arrayList!![position]!!.cart_qty
            var amounts = arrayList!![position]!!.product_amount
            if (qty >= 1) {
                qty -= 1
                amounts = (qty * amounts.toInt()).toString()
                cartViewModel.updateCart(
                    ProductEntity(
                        id,
                        arrayList!![position]!!.product_image,
                        arrayList!![position]!!.product_name,
                        arrayList!![position]!!.product_amount,
                        arrayList!![position]!!.product_description,
                        arrayList!![position]!!.product_category,
                        arrayList!![position]!!.product_userid,
                        arrayList!![position]!!.product_save,
                        qty,
                        amounts,
                        0,
                        arrayList!![position]!!.cart_order
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return productList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.cart_item_name)
        var amount: TextView = itemView.findViewById(R.id.cart_item_price)
        var total: TextView = itemView.findViewById(R.id.cart_total_price)
        var image: ImageView = itemView.findViewById(R.id.cart_item_iv)
        val cartQty: TextView = itemView.findViewById(R.id.cart_item_qty)
        val cartPlus: ImageView = itemView.findViewById(R.id.cart_item_plus)
        val cartMinus: ImageView = itemView.findViewById(R.id.cart_item_minus)
    }

    private fun getImage(byteArray: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
    }

    class ProductComparator : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductEntity,
            newItem: ProductEntity
        ): Boolean {
            return oldItem.product_image.contentEquals(newItem.product_image)
        }
    }

    fun submitLIst(arrayList: List<ProductEntity>) {
        this.arrayList = arrayList
        this.notifyDataSetChanged()
    }
}
