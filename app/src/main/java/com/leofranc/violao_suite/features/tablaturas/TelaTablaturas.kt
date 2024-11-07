package com.leofranc.violao_suite.features.tablaturas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.leofranc.violao_suite.data.dao.TablaturaDao
import com.leofranc.violao_suite.data.database.DatabaseProvider
import com.leofranc.violao_suite.data.model.Tablatura
import java.util.concurrent.Executors

private val databaseWriteExecutor = Executors.newSingleThreadExecutor()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TelaTablaturas() {
    val context = LocalContext.current
    val tablaturaDao = DatabaseProvider.getDatabase(context).tablaturaDao()
    val tablaturas by tablaturaDao.getAllTablaturas().collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var tablaturaSelecionada by remember { mutableStateOf<Tablatura?>(null) }

    if (tablaturaSelecionada == null) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar Tablatura")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text("Lista de Tablaturas", style = MaterialTheme.typography.h6)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tablaturas) { tablatura ->
                        // Define um tamanho fixo para cada item da grade
                        Box(
                            modifier = Modifier
                                .width(100.dp) // Define uma largura fixa para cada item
                                .height(140.dp) // Define uma altura fixa para cada item
                                .clickable {
                                    tablaturaSelecionada = tablatura
                                },
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                // Limita o nome e conteúdo a uma linha com overflow
                                Text(
                                    text = if (tablatura.nome.length > 8) tablatura.nome.take(8) + "..." else tablatura.nome,
                                    style = MaterialTheme.typography.body1,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (tablatura.texto.length > 8) tablatura.texto.take(8) + "..." else tablatura.texto,
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                // Espaço para empurrar o botão de exclusão para o final
                                Spacer(modifier = Modifier.weight(1f))

                                // Botão de exclusão
                                IconButton(
                                    onClick = {
                                        databaseWriteExecutor.execute {
                                            try {
                                                tablaturaDao.deleteTablatura(tablatura)
                                                Log.d("TelaTablaturas", "Tablatura excluída com sucesso")
                                            } catch (e: Exception) {
                                                Log.e("TelaTablaturas", "Erro ao excluir tablatura: ${e.message}")
                                            }
                                        }
                                    },
                                    modifier = Modifier.size(24.dp) // Define um tamanho fixo para o ícone
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Excluir Tablatura",
                                        tint = MaterialTheme.colors.error
                                    )
                                }
                            }
                        }
                    }
                }

                if (showDialog) {
                    AdicionarTablaturaDialog(
                        onDismiss = { showDialog = false },
                        onAdd = { nome, texto ->
                            databaseWriteExecutor.execute {
                                try {
                                    tablaturaDao.insertTablatura(Tablatura(nome = nome, texto = texto))
                                    Log.d("TelaTablaturas", "Tablatura adicionada com sucesso")
                                } catch (e: Exception) {
                                    Log.e("InsertTablaturaSection", "Erro ao inserir tablatura: ${e.message}")
                                }
                            }
                            showDialog = false
                        }
                    )
                }
            }
        }
    } else {
        TablaturaDetalheScreen(
            tablatura = tablaturaSelecionada!!,
            onSave = { nome, texto ->
                tablaturaSelecionada?.let { tablatura ->
                    databaseWriteExecutor.execute {
                        try {
                            tablaturaDao.updateTablatura(tablatura.copy(nome = nome, texto = texto))
                        } catch (e: Exception) {
                            Log.e("TablaturaDetalheScreen", "Erro ao atualizar tablatura: ${e.message}")
                        }
                    }
                    tablaturaSelecionada = null
                }
            },
            onCancel = { tablaturaSelecionada = null }
        )
    }
}


@Composable
fun AdicionarTablaturaDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var texto by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova Tablatura") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome da Tablatura") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = texto,
                    onValueChange = { texto = it },
                    label = { Text("Conteúdo da Tablatura") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onAdd(nome, texto) }) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun TablaturaDetalheScreen(
    tablatura: Tablatura,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var nome by remember { mutableStateOf(tablatura.nome) }
    var texto by remember { mutableStateOf(tablatura.texto) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (isEditing) "Editar Tablatura" else "Visualizar Tablatura") },
                navigationIcon = { // Botão de Voltar
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { isEditing = !isEditing }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Visibility else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Visualizar" else "Editar"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = { Text("Conteúdo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        maxLines = Int.MAX_VALUE
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            onSave(nome, texto)
                            isEditing = false
                        }) {
                            Text("Salvar")
                        }
                    }
                } else {
                    Text(text = nome, style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = texto,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}
