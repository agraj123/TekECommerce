package com.example.teke.Product

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class CartRepo(private val productDao: ProductDao) {

    fun allProduct(): Flow<List<ProductEntity>> {
        return productDao.fetchSave()
    }

    fun wishList(): Flow<List<ProductEntity>> {
        return productDao.fetchWish()
    }

    @WorkerThread
    fun updateCart(productEntity: ProductEntity) {
        productDao.ucart(productEntity)
    }
}