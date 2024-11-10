package com.leofranc.violao_suite.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "posicao",
    foreignKeys = [ForeignKey(
        entity = Tablatura::class,
        parentColumns = ["id"],
        childColumns = ["tablaturaId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Posicao(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tablaturaId: Long,  // Relacionamento com a Tablatura
    val corda1: Int = -1,   // Casa para a primeira corda (valores de 0 a 22, -1 para corda solta)
    val corda2: Int = -1,
    val corda3: Int = -1,
    val corda4: Int = -1,
    val corda5: Int = -1,
    val corda6: Int = -1
)
