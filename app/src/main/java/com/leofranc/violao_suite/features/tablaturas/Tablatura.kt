package com.leofranc.violao_suite.features.tablaturas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tablaturas")
data class Tablatura(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String = "Título padrão",
    val conteudo: String = "Conteúdo padrão",
    val caminhoImagem: String = "imagem_padrao"
)
