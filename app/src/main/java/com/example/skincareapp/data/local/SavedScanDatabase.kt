package com.example.skincareapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SavedScanEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SavedScanDatabase : RoomDatabase() {

    abstract fun savedScanDao(): SavedScanDao

    companion object {

        @Volatile
        private var INSTANCE: SavedScanDatabase? = null

        fun getDatabase(context: Context): SavedScanDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    SavedScanDatabase::class.java,
                    "saved_scans_database"
                ).build()

                INSTANCE = database
                database
            }
        }
    }
}