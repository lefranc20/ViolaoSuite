package com.leofranc.violao_suite.modules

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leofranc.violao_suite.data.model.TablaturaData
import com.leofranc.violao_suite.data.model.Secao
import com.leofranc.violao_suite.data.model.Nota

@Composable
fun EditDialog(
    tablatura: TablaturaData,
    onDismiss: () -> Unit,
    onSave: (TablaturaData) -> Unit
) {
    var titulo by remember { mutableStateOf(tablatura.titulo) }
    var descricao by remember { mutableStateOf(tablatura.descricao) }
    var secoes by remember { mutableStateOf(tablatura.secoes.toMutableList()) } // Converte para MutableList

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Tablatura") },
        text = {
            Column {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Editor de Tablatura com suporte para edição e remoção de notas
                TablaturaEditor(
                    secoes = secoes, // Passa a lista de seções editável
                    onNoteChange = { secaoPosicao, cordaIndex, novaCasa ->
                        // Atualiza a casa da nota na corda específica
                        secoes = secoes.mapIndexed { index, secao ->
                            if (index == secaoPosicao) {
                                secao.copy(notas = secao.notas.mapIndexed { i, nota ->
                                    if (i == cordaIndex) nota.copy(casa = novaCasa) else nota
                                }.toMutableList())
                            } else {
                                secao
                            }
                        }.toMutableList()
                    },
                    onNoteDelete = { secaoPosicao, cordaIndex ->
                        // Define a casa como -1 para remover a nota
                        secoes = secoes.mapIndexed { index, secao ->
                            if (index == secaoPosicao) {
                                secao.copy(notas = secao.notas.mapIndexed { i, nota ->
                                    if (i == cordaIndex) nota.copy(casa = -1) else nota
                                }.toMutableList())
                            } else {
                                secao
                            }
                        }.toMutableList()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botão para remover uma seção inteira
                Button(
                    onClick = {
                        // Remove a última seção da lista (ou personalize conforme necessário)
                        if (secoes.isNotEmpty()) {
                            secoes.removeLast() // Remover a última seção
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Remover Seção")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedTablatura = tablatura.copy(
                    titulo = titulo,
                    descricao = descricao,
                    secoes = secoes // Atualiza as seções com as alterações
                )
                onSave(updatedTablatura)
                onDismiss()
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
