package com.leofranc.violao_suite.features.tablaturas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import com.leofranc.violao_suite.data.dao.TablaturaDao
import com.leofranc.violao_suite.data.database.DatabaseProvider
import com.leofranc.violao_suite.data.model.Tablatura
import java.util.concurrent.Executors

private val databaseWriteExecutor = Executors.newSingleThreadExecutor()

@Composable
fun TelaTablaturas() {
    val context = LocalContext.current
    val tablaturaDao = DatabaseProvider.getDatabase(context).tablaturaDao()
    val tablaturas by tablaturaDao.getAllTablaturas().collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) } // Controla a exibição do diálogo
    var tablaturaSelecionada by remember { mutableStateOf<Tablatura?>(null) }

    Scaffold(
        floatingActionButton = {
            // Exibe o FAB apenas quando não há uma tablatura selecionada
            if (tablaturaSelecionada == null) {
                FloatingActionButton(
                    onClick = {
                        showDialog = true // Abre o diálogo de criação
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar Tablatura")
                }
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

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tablaturas) { tablatura ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { tablaturaSelecionada = tablatura },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = tablatura.nome)
                            Text(text = tablatura.texto)
                        }
                        IconButton(onClick = {
                            // Executa a exclusão da tablatura
                            databaseWriteExecutor.execute {
                                try {
                                    tablaturaDao.deleteTablatura(tablatura)
                                } catch (e: Exception) {
                                    Log.e("TablaturaListSection", "Erro ao deletar tablatura: ${e.message}")
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Deletar",
                                tint = MaterialTheme.colors.error
                            )
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            // Diálogo para adicionar nova tablatura
            if (showDialog) {
                AdicionarTablaturaDialog(
                    onDismiss = { showDialog = false },
                    onAdd = { nome, texto ->
                        databaseWriteExecutor.execute {
                            try {
                                tablaturaDao.insertTablatura(Tablatura(nome = nome, texto = texto))
                            } catch (e: Exception) {
                                Log.e("InsertTablaturaSection", "Erro ao inserir tablatura: ${e.message}")
                            }
                        }
                        showDialog = false
                    }
                )
            }

            // Exibe a tela de detalhes se uma tablatura for selecionada
            tablaturaSelecionada?.let { tablatura ->
                TablaturaDetalheScreen(
                    tablatura = tablatura,
                    onSave = { nome, texto ->
                        databaseWriteExecutor.execute {
                            try {
                                tablaturaDao.updateTablatura(tablatura.copy(nome = nome, texto = texto))
                            } catch (e: Exception) {
                                Log.e("TablaturaDetalheScreen", "Erro ao atualizar tablatura: ${e.message}")
                            }
                        }
                        tablaturaSelecionada = null // Volta para a listagem após salvar
                    },
                    onCancel = { tablaturaSelecionada = null } // Volta para a listagem ao cancelar
                )
            }
        }
    }
}

// Função para inserir uma nova tablatura diretamente
fun adicionarTablatura(tablaturaDao: TablaturaDao) {
    val novaTablatura = Tablatura(nome = "Nova Tablatura", texto = "Conteúdo de exemplo") // Define valores fixos para teste
    databaseWriteExecutor.execute {
        try {
            tablaturaDao.insertTablatura(novaTablatura)
            Log.d("TelaTablaturas", "Tablatura adicionada com sucesso") // Log de confirmação
        } catch (e: Exception) {
            Log.e("TelaTablaturas", "Erro ao adicionar tablatura: ${e.message}")
        }
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
