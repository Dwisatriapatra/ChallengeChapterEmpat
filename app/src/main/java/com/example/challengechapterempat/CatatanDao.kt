package com.example.challengechapterempat

import androidx.room.*

@Dao
interface CatatanDao{
    @Insert fun insertCatatan(catatan: Catatan) : Long
    @Query("SELECT * FROM Catatan") fun getAllCatatan() : List<Catatan>
    @Delete fun deleteDataCatatan(catatan: Catatan) : Int
    @Update fun updateDataCatatan(catatan: Catatan) : Int
}