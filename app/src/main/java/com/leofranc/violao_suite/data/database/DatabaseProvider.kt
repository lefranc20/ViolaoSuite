package com.leofranc.violao_suite.data.database

import android.content.Context
import androidx.room.Room

/*
Este arquivo fornece uma instância singleton do AppDatabase para o aplicativo inteiro,
evitando a criação de múltiplas instâncias do banco de dados, o que economiza recursos.
 */

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
