package com.leofranc.violao_suite.ui.tablaturas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditTablaturaDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Nova Tablatura") },
        text = {
            Column {
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(titulo, descricao)
                }
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Cancelar")
            }
        }
    )
}
