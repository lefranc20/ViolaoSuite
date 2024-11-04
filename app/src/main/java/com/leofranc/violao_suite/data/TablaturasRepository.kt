package com.leofranc.violao_suite.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class Tablatura(val titulo: String, val instrumento: String, val cordas: List<List<Int>>)

class TablaturasRepository(private val context: Context) {
    private val gson = Gson()
    private val file = File(context.filesDir, "tablaturas.json")

    fun loadTablaturas(): List<Tablatura> {
        return if (file.exists()) {
            val json = file.readText()
            gson.fromJson(json, object : TypeToken<List<Tablatura>>() {}.type)
        } else emptyList()
    }

    fun saveTablaturas(tablaturas: List<Tablatura>) {
        val json = gson.toJson(tablaturas)
        file.writeText(json)
    }

    fun addTablatura(tablatura: Tablatura) {
        val tablaturas = loadTablaturas().toMutableList()
        tablaturas.add(tablatura)
        saveTablaturas(tablaturas)
    }

    fun editTablatura(oldTitulo: String, updatedTablatura: Tablatura) {
        val tablaturas = loadTablaturas().toMutableList()
        val index = tablaturas.indexOfFirst { it.titulo == oldTitulo }
        if (index != -1) {
            tablaturas[index] = updatedTablatura
            saveTablaturas(tablaturas)
        }
    }

    fun removeTablatura(titulo: String) {
        val tablaturas = loadTablaturas().filter { it.titulo != titulo }
        saveTablaturas(tablaturas)
    }
}
