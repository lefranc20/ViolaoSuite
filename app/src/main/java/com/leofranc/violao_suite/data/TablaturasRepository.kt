package com.leofranc.violao_suite.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leofranc.violao_suite.data.model.TablaturaData
import java.io.File

class TablaturasRepository(private val context: Context) {

    private val fileName = "tablaturas.json"

    // Carrega todas as tablaturas do arquivo JSON
    fun loadAllTablaturas(): List<TablaturaData> {
        return try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                val json = file.readText()
                val type = object : TypeToken<List<TablaturaData>>() {}.type
                Gson().fromJson(json, type)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Salva a lista de tablaturas no arquivo JSON
    fun saveTablaturas(tablaturas: List<TablaturaData>) {
        try {
            val file = File(context.filesDir, fileName)
            file.writeText(Gson().toJson(tablaturas))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
