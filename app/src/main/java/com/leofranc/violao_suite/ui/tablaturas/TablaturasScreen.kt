package com.leofranc.violao_suite.ui.tablaturas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leofranc.violao_suite.model.Tablatura
import com.leofranc.violao_suite.viewmodel.TablaturaViewModel

@Composable
fun TablaturasScreen(navController: NavController, viewModel: TablaturaViewModel) {
    val tablaturas by viewModel.tablaturas.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Tablaturas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Tablatura")
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                // Lista de tablaturas
                LazyColumn {
                    items(tablaturas) { tablatura ->
                        TablaturaItem(
                            tablatura = tablatura,
                            onClick = {
                                // Navega para a tela de edição da tablatura
                                navController.navigate("tablatura/${tablatura.id}")
                            }
                        )
                    }
                }
            }

            // Diálogo para adicionar uma nova tablatura
            if (showDialog) {
                AdicionarTablaturaDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = { titulo, descricao ->
                        viewModel.adicionarTablatura(titulo, descricao)
                        showDialog = false
                    }
                )
            }
        }
    )
}

@Composable
fun AdicionarTablaturaDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Tablatura") },
        text = {
            Column {
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") }
                )
                TextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(titulo, descricao) }) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun TablaturaItem(tablatura: Tablatura, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = tablatura.titulo, style = MaterialTheme.typography.subtitle1)
            tablatura.descricao?.let {
                Text(text = it, style = MaterialTheme.typography.body2)
            }
        }
    }
}
