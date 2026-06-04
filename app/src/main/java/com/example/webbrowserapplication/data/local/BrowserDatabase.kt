package com.example.webbrowserapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BrowserDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object{

        @Volatile
        private var INSTANCE : BrowserDatabase? = null

        fun getDatabase(context: Context): BrowserDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BrowserDatabase::class.java,
                    "browser_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}