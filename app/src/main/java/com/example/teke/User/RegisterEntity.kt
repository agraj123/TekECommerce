package com.example.teke.User

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Register_users_table")
data class RegisterEntity(

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,

    @ColumnInfo(name = "name")
    var Name: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "password_text")
    var passwrd: String,

    @ColumnInfo(name = "confirm_password_text")
    var confirmPass: String,
)