package com.example.challengechapterempat

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CatatanDao{
    @Insert fun insertCatatan(catatan: Catatan) : Long
}