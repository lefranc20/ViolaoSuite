package com.leofranc.violao_suite.features.tablaturas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TablaturaViewModel(private val dao: TablaturaDao) : ViewModel() {

    // Colete o Flow diretamente do DAO
    val tablaturas: StateFlow<List<Tablatura>> = dao.obterTodas()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun adicionarTablatura(titulo: String?, conteudo: String?, caminhoImagem: String?) {
        // Use valores padrão se algum campo for nulo
        val safeTitulo = titulo ?: "Título padrão"
        val safeConteudo = conteudo ?: "Conteúdo padrão"
        val safeCaminhoImagem = caminhoImagem ?: "imagem_padrao"

        val tablatura = Tablatura(
            titulo = safeTitulo,
            conteudo = safeConteudo,
            caminhoImagem = safeCaminhoImagem
        )

        viewModelScope.launch {
            dao.inserir(tablatura)
        }
    }


    fun atualizarTablatura(tablatura: Tablatura) {
        viewModelScope.launch {
            dao.atualizar(tablatura)
        }
    }

    fun deletarTablatura(tablatura: Tablatura) {
        viewModelScope.launch {
            dao.deletar(tablatura)
        }
    }

    fun obterTablaturas() = dao.obterTodas().map { lista ->
        lista.map { tablatura ->
            Tablatura(
                id = tablatura.id,
                titulo = tablatura.titulo ?: "Título padrão",
                conteudo = tablatura.conteudo ?: "Conteúdo padrão",
                caminhoImagem = tablatura.caminhoImagem ?: "imagem_padrao"
            )
        }
    }
}

