// TablaturaData.kt
package com.leofranc.violao_suite.data.model

data class TablaturaData(
    val id: String,
    var titulo: String,
    var descricao: String = "", // Inclui a descrição com valor padrão vazio
    var secoes: MutableList<Secao> = mutableListOf() // Usa MutableList para permitir modificações
)
