package com.leofranc.violao_suite.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tablatura_table")
data class Tablatura(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val texto: String
)
