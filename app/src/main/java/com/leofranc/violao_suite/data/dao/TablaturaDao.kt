package com.leofranc.violao_suite.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
// ou import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.leofranc.violao_suite.data.model.Tablatura


@Dao
interface TablaturaDao {
    @Insert
    fun insertTablatura(tablatura: Tablatura): Long

    @Query("SELECT * FROM tablatura_table")
    fun getAllTablaturas(): Flow<List<Tablatura>>

    @Delete
    fun deleteTablatura(tablatura: Tablatura): Int
}