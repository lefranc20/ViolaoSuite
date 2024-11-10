package com.leofranc.violao_suite.data

import androidx.room.*
import com.leofranc.violao_suite.model.Posicao
import com.leofranc.violao_suite.model.Tablatura
import kotlinx.coroutines.flow.Flow

@Dao
interface TablaturaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirTablatura(tablatura: Tablatura): Long

    @Query("SELECT * FROM tablatura WHERE id = :tablaturaId")
    fun obterTablaturaPorId(tablaturaId: Long): Tablatura?

    @Delete
    fun deletarTablatura(tablatura: Tablatura): Int

    @Update
    fun atualizarTablatura(tablatura: Tablatura): Int

    @Query("SELECT * FROM tablatura ORDER BY id")
    fun obterTodasTablaturas(): Flow<List<Tablatura>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirPosicao(posicao: Posicao): Long

    @Query("SELECT * FROM posicao WHERE tablaturaId = :tablaturaId ORDER BY id")
    fun obterPosicoesPorTablatura(tablaturaId: Long): Flow<List<Posicao>>

    @Update
    fun atualizarPosicao(posicao: Posicao): Int

    @Delete
    fun deletarPosicao(posicao: Posicao): Int
}
