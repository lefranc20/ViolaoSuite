package com.leofranc.violao_suite.repository

import com.leofranc.violao_suite.data.TablaturaDao
import com.leofranc.violao_suite.model.Tablatura
import com.leofranc.violao_suite.model.Posicao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow

class TablaturaRepository(private val dao: TablaturaDao) {

    // Inserir uma nova Tablatura
    fun inserirTablatura(tablatura: Tablatura): Long = dao.inserirTablatura(tablatura)

    // Obter uma Tablatura específica pelo ID
    suspend fun obterTablaturaPorId(tablaturaId: Long): Tablatura? = withContext(Dispatchers.IO) {
        dao.obterTablaturaPorId(tablaturaId)
    }

    // Excluir uma tablatura
    suspend fun deletarTablatura(tablatura: Tablatura): Int = withContext(Dispatchers.IO) {
        dao.deletarTablatura(tablatura)
    }

    // Atualizar uma tablatura
    suspend fun atualizarTablatura(tablatura: Tablatura): Int = withContext(Dispatchers.IO) {
        dao.atualizarTablatura(tablatura)
    }

    // Obter todas as tablaturas como Flow
    fun obterTodasTablaturas(): Flow<List<Tablatura>> = dao.obterTodasTablaturas()

    // Inserir uma nova posição
    suspend fun inserirPosicao(posicao: Posicao): Long = withContext(Dispatchers.IO) {
        dao.inserirPosicao(posicao)
    }

    // Obter posições para uma tablatura específica
    fun obterPosicoesPorTablatura(tablaturaId: Long): Flow<List<Posicao>> = dao.obterPosicoesPorTablatura(tablaturaId)

    // Atualizar uma posição
    suspend fun atualizarPosicao(posicao: Posicao): Int = withContext(Dispatchers.IO) {
        dao.atualizarPosicao(posicao)
    }

    // Excluir uma posição
    suspend fun deletarPosicao(posicao: Posicao): Int = withContext(Dispatchers.IO) {
        dao.deletarPosicao(posicao)
    }
}
