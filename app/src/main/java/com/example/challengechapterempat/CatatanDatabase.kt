package com.example.challengechapterempat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//ROOM DATABASE for Catatan
@Database(entities = [Catatan::class], version = 1)
abstract class CatatanDatabase : RoomDatabase() {
    //function to call DAO interface, DAO interface must have been defined
    abstract fun catatanDao() : CatatanDao

    //function to get and destroy instance of catatan database
    companion object{
        private var INSTANCE : CatatanDatabase? = null
        fun getInstance(context : Context):CatatanDatabase? {
            if (INSTANCE == null){
                synchronized(CatatanDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CatatanDatabase::class.java,"Catatan.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

//        fun destroyInstance(){
//            INSTANCE = null
//        }
    }
}