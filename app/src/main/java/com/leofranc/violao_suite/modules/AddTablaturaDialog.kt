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
fun AddTablaturaDialog(onSave: (TablaturaData) -> Unit) {
    // Variáveis para os campos do diálogo
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    val secoes = remember { mutableStateOf(mutableListOf<Secao>()) } // Inicializa como MutableList

    // Função para salvar a nova tablatura
    fun salvarTablatura() {
        val novaTablatura = TablaturaData(
            id = System.currentTimeMillis().toString(), // Gera um ID único
            titulo = titulo,
            descricao = descricao, // Inclui a descrição
            secoes = secoes.value // Usa MutableList
        )
        onSave(novaTablatura) // Passa a nova tablatura para ser salva
    }

    // Layout do diálogo para inserir título, descrição, etc.
    AlertDialog(
        onDismissRequest = { /* Código para fechar o diálogo */ },
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
                // Outros campos, como adicionar seções ou notas
            }
        },
        confirmButton = {
            Button(onClick = { salvarTablatura() }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = { /* Código para fechar o diálogo */ }) {
                Text("Cancelar")
            }
        }
    )
}
