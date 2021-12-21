package com.example.teke.User

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teke.Product.ProductDao
import com.example.teke.Product.ProductEntity

@Database(entities = [RegisterEntity::class, ProductEntity::class], version = 12)
abstract class RegisterDatabase : RoomDatabase() {

    abstract fun RegisterDatabaseDao(): RegisterDatabaseDao
    abstract fun ProductDao(): ProductDao

    companion object {

        private var instance: RegisterDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RegisterDatabase {
            instance = Room.databaseBuilder(
                context,
                RegisterDatabase::class.java,
                "user_database"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
            return instance as RegisterDatabase
        }
    }
}