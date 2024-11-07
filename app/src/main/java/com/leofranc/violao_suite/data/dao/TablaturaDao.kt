package com.leofranc.violao_suite.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
// ou import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.leofranc.violao_suite.data.model.Tablatura


@Dao
interface TablaturaDao {
    @Insert
    fun insertTablatura(tablatura: Tablatura): Long

    @Query("SELECT * FROM tablatura_table")
    fun getAllTablaturas(): Flow<List<Tablatura>>

    @Update
    fun updateTablatura(tablatura: Tablatura)

    // Função para buscar uma tablatura específica pelo ID
    @Query("SELECT * FROM tablatura_table WHERE id = :id")
    fun getTablaturaById(id: Int): Tablatura

    @Delete
    fun deleteTablatura(tablatura: Tablatura): Int
}