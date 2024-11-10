package com.leofranc.violao_suite.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tablatura")
data class Tablatura(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titulo: String,
    val descricao: String?
)
