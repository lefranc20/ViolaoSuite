package com.leofranc.violao_suite.modules

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leofranc.violao_suite.R
import java.io.File

data class Tablatura(val titulo: String, val cordas: List<MutableList<String>>)

@Composable
fun TablaturasScreen() {
    val context = LocalContext.current
    var tablaturas by remember { mutableStateOf(loadTablaturas(context)) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var tablaturaEmEdicao by remember { mutableStateOf<Tablatura?>(null) }

    fun adicionarTablatura(titulo: String) {
        val novaTablatura = Tablatura(titulo, List(6) { MutableList(22) { "" } })
        tablaturas = tablaturas + novaTablatura
        saveTablaturas(context, tablaturas)
    }

    fun atualizarTablatura(tablatura: Tablatura, novoTitulo: String) {
        tablaturas = tablaturas.map {
            if (it == tablatura) it.copy(titulo = novoTitulo) else it
        }
        saveTablaturas(context, tablaturas)
    }

    fun editarNotasTablatura(tablatura: Tablatura, novaCordas: List<MutableList<String>>) {
        tablaturas = tablaturas.map {
            if (it == tablatura) it.copy(cordas = novaCordas) else it
        }
        saveTablaturas(context, tablaturas)
    }

    fun removerTablatura(tablatura: Tablatura) {
        tablaturas = tablaturas.filter { it != tablatura }
        saveTablaturas(context, tablaturas)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Lista de Tablaturas",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            color = Color.Black
        )

        // Botão de adição
        Button(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Adicionar Tablatura")
        }

        // Lista de tablaturas
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(tablaturas) { tablatura ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            tablaturaEmEdicao = tablatura
                            showEditDialog = true
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_tablatura1),
                        contentDescription = tablatura.titulo,
                        modifier = Modifier.size(96.dp)
                    )
                    Text(
                        text = tablatura.titulo,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    // Botão para remover
                    IconButton(onClick = { removerTablatura(tablatura) }) {
                        Icon(painterResource(R.drawable.ic_delete), contentDescription = "Remover")
                    }
                }
            }
        }

        // Diálogo para adicionar tablatura
        if (showAddDialog) {
            var titulo by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Adicionar Nova Tablatura") },
                text = {
                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        adicionarTablatura(titulo)
                        showAddDialog = false
                    }) {
                        Text("Adicionar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showAddDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Diálogo para editar tablatura
        if (showEditDialog && tablaturaEmEdicao != null) {
            var novoTitulo by remember { mutableStateOf(tablaturaEmEdicao!!.titulo) }
            var cordas by remember { mutableStateOf(tablaturaEmEdicao!!.cordas.map { it.toMutableList() }) }

            AlertDialog(
                onDismissRequest = {
                    showEditDialog = false
                    tablaturaEmEdicao = null
                },
                title = { Text("Editar Tablatura") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = novoTitulo,
                            onValueChange = { novoTitulo = it },
                            label = { Text("Título") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Editar Notas:")
                        cordas.forEachIndexed { cordaIndex, corda ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Corda ${cordaIndex + 1}:", fontSize = 14.sp)
                                corda.forEachIndexed { casaIndex, casa ->
                                    OutlinedTextField(
                                        value = casa,
                                        onValueChange = { novaNota ->
                                            cordas[cordaIndex][casaIndex] = novaNota
                                        },
                                        modifier = Modifier.width(40.dp),
                                        singleLine = true,
                                        label = { Text("$casaIndex") }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        atualizarTablatura(tablaturaEmEdicao!!, novoTitulo)
                        editarNotasTablatura(tablaturaEmEdicao!!, cordas)
                        showEditDialog = false
                        tablaturaEmEdicao = null
                    }) {
                        Text("Salvar")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showEditDialog = false
                        tablaturaEmEdicao = null
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

// Funções para carregar e salvar JSON
fun loadTablaturas(context: Context): List<Tablatura> {
    val file = File(context.filesDir, "tablaturas.json")
    return if (file.exists()) {
        val json = file.readText()
        val type = object : TypeToken<List<Tablatura>>() {}.type
        Gson().fromJson(json, type)
    } else {
        emptyList()
    }
}

fun saveTablaturas(context: Context, tablaturas: List<Tablatura>) {
    val file = File(context.filesDir, "tablaturas.json")
    file.writeText(Gson().toJson(tablaturas))
}
