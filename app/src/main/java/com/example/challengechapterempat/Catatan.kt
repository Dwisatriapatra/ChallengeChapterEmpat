package com.example.challengechapterempat

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
//Catatan data class, act as entity. Extend to parcelable
@Entity
@Parcelize
data class Catatan(
    @PrimaryKey(autoGenerate = true) var id : Int?,
    @ColumnInfo(name = "judul") var judul : String?,
    @ColumnInfo(name = "catatan") var catatan : String?
) : Parcelable
