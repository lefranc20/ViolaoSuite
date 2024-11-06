package com.leofranc.violao_suite.modules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leofranc.violao_suite.data.model.Nota
import com.leofranc.violao_suite.data.model.Secao
import com.leofranc.violao_suite.data.model.TablaturaData

@Composable
fun AddTablaturaDialog(
    onDismiss: () -> Unit,
    onSave: (TablaturaData) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var secoes by remember { mutableStateOf(emptyList<Secao>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Nova Tablatura") },
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

                // Adicionando uma seção inicial com notas vazias para a nova tablatura
                Button(onClick = {
                    val novaSecao = Secao(
                        posicao = secoes.size + 1,
                        notas = List(6) { Nota(corda = it + 1, casa = -1) }
                    )
                    secoes = secoes + novaSecao
                }) {
                    Text("Adicionar Seção")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val newTablatura = TablaturaData(
                    id = System.currentTimeMillis().toString(), // Gera um ID único com base no tempo
                    titulo = titulo,
                    descricao = descricao,
                    secoes = secoes
                )
                onSave(newTablatura)
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
