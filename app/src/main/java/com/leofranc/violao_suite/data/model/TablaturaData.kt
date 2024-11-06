// Tablatura.kt
package com.leofranc.violao_suite.data.model

data class Nota(
    val corda: Int,
    val casa: Int
)

data class Secao(
    val posicao: Int,
    val notas: List<Nota>
)

data class TablaturaData(
    val id: String,
    val titulo: String,
    val descricao: String,
    val secoes: List<Secao>
)

data class TablaturasResponse(
    val dados: TablaturaData
)
