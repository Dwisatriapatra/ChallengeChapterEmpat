package com.example.challengechapterempat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
//DAO interface for User ROOM database
@Dao
interface UserDao {
    @Insert fun insertUser(user: User) : Long
    @Query("SELECT username FROM User WHERE User.email = :email AND User.password = :password")
    fun checkUserLoginData(email: String, password : String) : String
}