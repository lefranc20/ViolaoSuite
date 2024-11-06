package com.leofranc.violao_suite.features.tablaturas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditorTablatura(
    tablatura: Tablatura,
    onSave: (Tablatura) -> Unit,
    onDelete: (Tablatura) -> Unit,
    onClose: () -> Unit
) {
    var titulo by remember { mutableStateOf(tablatura.titulo) }
    var conteudo by remember { mutableStateOf(tablatura.conteudo) }
    var caminhoImagem by remember { mutableStateOf(tablatura.caminhoImagem) }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = conteudo,
            onValueChange = { conteudo = it },
            label = { Text("Conteúdo") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = caminhoImagem,
            onValueChange = { caminhoImagem = it },
            label = { Text("Caminho da Imagem") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            onSave(tablatura.copy(titulo = titulo, conteudo = conteudo, caminhoImagem = caminhoImagem))
            onClose()
        }) {
            Text("Salvar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onDelete(tablatura); onClose() }) {
            Text("Deletar")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onClose) {
            Text("Fechar")
        }
    }
}
