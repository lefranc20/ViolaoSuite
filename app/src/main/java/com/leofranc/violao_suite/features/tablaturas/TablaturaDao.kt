package com.leofranc.violao_suite.features.tablaturas

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TablaturaDao {

    @Insert
    fun inserir(tablatura: Tablatura): Long

    @Update
    fun atualizar(tablatura: Tablatura): Int

    @Delete
    fun deletar(tablatura: Tablatura): Int


    // @Query("SELECT * FROM tablaturas")
    @Query("SELECT id, COALESCE(titulo, 'Título padrão') AS titulo, COALESCE(conteudo, 'Conteúdo padrão') AS conteudo, COALESCE(caminhoImagem, 'imagem_padrao') AS caminhoImagem FROM tablaturas")
    fun obterTodas(): Flow<List<Tablatura>>
}
