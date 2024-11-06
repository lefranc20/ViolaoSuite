package com.leofranc.violao_suite.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leofranc.violao_suite.data.dao.TablaturaDao
import com.leofranc.violao_suite.data.model.Tablatura

@Database(entities = [Tablatura::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tablaturaDao(): TablaturaDao
}