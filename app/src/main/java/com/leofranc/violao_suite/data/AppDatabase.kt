package com.leofranc.violao_suite.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.leofranc.violao_suite.model.Tablatura
import com.leofranc.violao_suite.model.Posicao

@Database(entities = [Tablatura::class, Posicao::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tablaturaDao(): TablaturaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "violao_suite_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
