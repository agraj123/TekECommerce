package com.example.teke

import android.app.Application
import com.example.teke.Product.CartRepo
import com.example.teke.User.RegisterDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class shopApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { RegisterDatabase.getInstance(this) }
    val repository by lazy { CartRepo(database.ProductDao()) }
}