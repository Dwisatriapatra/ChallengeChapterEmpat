package com.example.challengechapterempat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatatanDao{
    @Insert fun insertCatatan(catatan: Catatan) : Long
    @Query("SELECT * FROM Catatan") fun getAllCatatan() : List<Catatan>
    @Delete fun deleteDataCatatan(catatan: Catatan) : Int
}