package com.example.teke.Product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0,

    @ColumnInfo(name = "product_image", typeAffinity = ColumnInfo.BLOB)
    val product_image: ByteArray? = null,

    @ColumnInfo(name = "product_name")
    val product_name: String,

    @ColumnInfo(name = "product_amount")
    val product_amount: String,

    @ColumnInfo(name = "product_description")
    val product_description: String,

    @ColumnInfo(name = "product_category")
    val product_category: String,

    @ColumnInfo(name = "product_userid")
    val product_userid: Int,

//    @ColumnInfo(name = "fav")
//    var fav: Boolean,

    @ColumnInfo(name = "product_save")
    val product_save: Int,

    @ColumnInfo(name = "cart_qty")
    val cart_qty: Int,

    @ColumnInfo(name = "cart_total")
    val cart_total: String,

    @ColumnInfo(name = "product_placed")
    val product_placed: Int,

    @ColumnInfo(name = "cart_order")
    val cart_order: Int,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductEntity

        if (product_image != null) {
            if (other.product_image == null) return false
            if (!product_image.contentEquals(other.product_image)) return false
        } else if (other.product_image != null) return false

        return true
    }

    override fun hashCode(): Int {
        return product_image?.contentHashCode() ?: 0
    }
}
