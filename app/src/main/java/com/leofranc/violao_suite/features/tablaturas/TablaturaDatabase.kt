package com.leofranc.violao_suite.features.tablaturas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tablatura::class], version = 1)
abstract class TablaturaDatabase : RoomDatabase() {
    abstract fun tablaturaDao(): TablaturaDao

    companion object {
        @Volatile
        private var INSTANCE: TablaturaDatabase? = null

        fun getDatabase(context: Context): TablaturaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TablaturaDatabase::class.java,
                    "tablatura_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
