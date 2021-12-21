package com.example.teke.User

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RegisterDatabaseDao {

    @Insert
    fun insert(register: RegisterEntity)

    //@Delete
    //suspend  fun deleteSubscriber(register: RegisterEntity):Int

    @Query("SELECT * FROM Register_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<RegisterEntity>>

    @Query("DELETE FROM Register_users_table")
    fun deleteAll()

    @Query("SELECT * FROM Register_users_table WHERE name LIKE :userName")
    fun getUsername(userName: String): RegisterEntity?

    @Query("SELECT * FROM Register_users_table where email= :userEmail and password_text= :password")
    fun getUser(userEmail: String?, password: String?): RegisterEntity?

}
