package com.example.webbrowserapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Int =0,

    val url: String,
    val timeStamp : Long = System.currentTimeMillis()
)