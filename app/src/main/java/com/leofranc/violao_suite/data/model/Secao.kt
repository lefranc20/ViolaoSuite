// Secao.kt
package com.leofranc.violao_suite.data.model

data class Secao(
    val posicao: Int,
    val notas: MutableList<Nota> = mutableListOf() // Usa MutableList para permitir modificações
)
