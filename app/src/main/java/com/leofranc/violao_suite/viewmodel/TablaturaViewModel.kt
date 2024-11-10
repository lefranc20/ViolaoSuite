package com.leofranc.violao_suite.viewmodel

import androidx.lifecycle.*
import com.leofranc.violao_suite.repository.TablaturaRepository
import com.leofranc.violao_suite.model.Tablatura
import com.leofranc.violao_suite.model.Posicao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TablaturaViewModel(private val repository: TablaturaRepository) : ViewModel() {

    // LiveData para observar a lista de tablaturas
    val tablaturas: LiveData<List<Tablatura>> = repository.obterTodasTablaturas().asLiveData()
    private val _posicoes = MutableLiveData<List<Posicao>>()
    val posicoes: LiveData<List<Posicao>> = _posicoes

    // Método para adicionar uma nova Tablatura
    fun adicionarTablatura(titulo: String, descricao: String) {
        val novaTablatura = Tablatura(titulo = titulo, descricao = descricao)
        viewModelScope.launch(Dispatchers.IO) {  // Executa em um contexto de background
            repository.inserirTablatura(novaTablatura)
        }
    }

    suspend fun carregarTablatura(tablaturaId: Long): Tablatura? {
        return repository.obterTablaturaPorId(tablaturaId)
    }


    // Carregar posições para uma Tablatura específica
    fun carregarPosicoes(tablaturaId: Long) {
        viewModelScope.launch {
            repository.obterPosicoesPorTablatura(tablaturaId).collect { listaPosicoes ->
                _posicoes.value = listaPosicoes
            }
        }
    }

    // Adicionar uma nova posição à Tablatura
    fun adicionarPosicao(tablaturaId: Long) {
        viewModelScope.launch {
            val novaPosicao = Posicao(tablaturaId = tablaturaId)
            repository.inserirPosicao(novaPosicao)
            carregarPosicoes(tablaturaId)
        }
    }

    // Atualizar uma posição específica
    fun atualizarPosicao(posicao: Posicao) {
        viewModelScope.launch {
            repository.atualizarPosicao(posicao)
        }
    }

    // Salvar ou atualizar uma Tablatura (edições)
    fun salvarTablatura(tablatura: Tablatura) {
        viewModelScope.launch {
            repository.atualizarTablatura(tablatura)
        }
    }

    // Deletar uma Tablatura específica
    fun deletarTablatura(tablatura: Tablatura) {
        viewModelScope.launch {
            repository.deletarTablatura(tablatura)
        }
    }

    // Deletar uma posição específica dentro de uma Tablatura
    fun deletarPosicao(posicao: Posicao) {
        viewModelScope.launch {
            repository.deletarPosicao(posicao)
            carregarPosicoes(posicao.tablaturaId) // Recarrega as posições após a exclusão
        }
    }
}
