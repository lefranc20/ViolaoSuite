package com.leofranc.violao_suite.features.tablaturas

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.leofranc.violao_suite.data.dao.TablaturaDao
import com.leofranc.violao_suite.data.database.DatabaseProvider
import com.leofranc.violao_suite.data.model.Tablatura
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

private val databaseWriteExecutor = Executors.newSingleThreadExecutor()


@Composable
fun TelaTablaturas() {
    Log.d("TelaTablaturas", "Iniciando tela de tablaturas")

    // Obtenha o TablaturaDao diretamente no Composable
    val context = LocalContext.current
    val tablaturaDao = DatabaseProvider.getDatabase(context).tablaturaDao()

    // Função principal da tela que organiza a inserção e a lista de exibição
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Seção para inserir nova tablatura
        InsertTablaturaSection(tablaturaDao)

        Spacer(modifier = Modifier.height(24.dp))

        // Seção para exibir a lista de tablaturas
        TablaturaListSection(tablaturaDao)
    }
}

@Composable
fun InsertTablaturaSection(tablaturaDao: TablaturaDao) {
    var nome by remember { mutableStateOf("") }
    var texto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome da Tablatura") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Conteúdo da Tablatura") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    databaseWriteExecutor.execute {
                        try {
                            // Insere a nova tablatura no banco de dados
                            val novaTablatura = Tablatura(nome = nome, texto = texto)
                            tablaturaDao.insertTablatura(novaTablatura)

                            // Limpa os campos após a inserção (de volta na UI thread)
                            nome = ""
                            texto = ""
                        } catch (e: Exception) {
                            Log.e("InsertTablaturaSection", "Erro ao inserir tablatura: ${e.message}")
                        }
                    }
                }
            ) {
                Text("Adicionar Tablatura")
            }
        }
    }
}

@Composable
fun TablaturaListSection(tablaturaDao: TablaturaDao) {
    // Observa a lista de tablaturas armazenadas no banco de dados
    val tablaturas by tablaturaDao.getAllTablaturas().collectAsState(initial = emptyList())

    Column {
        Text("Lista de Tablaturas", style = MaterialTheme.typography.titleMedium)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tablaturas) { tablatura ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Exibe o nome e o conteúdo da tablatura
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Nome: ${tablatura.nome}")
                            Text(text = "Conteúdo: ${tablatura.texto}")
                        }
                        // Botão de exclusão
                        IconButton(onClick = {
                            // Executa a exclusão em uma thread secundária
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
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}
