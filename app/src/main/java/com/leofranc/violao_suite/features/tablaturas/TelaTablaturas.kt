package com.leofranc.violao_suite.features.tablaturas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leofranc.violao_suite.R

@Composable
fun TelaTablaturas(viewModel: TablaturaViewModel = viewModel()) {
    Log.d("TelaTablaturas", "Iniciando tela de tablaturas") // Novo log para testar
    val tablaturas by viewModel.tablaturas.collectAsState(initial = emptyList())

    // var editorAberto by remember { mutableStateOf<Tablatura?>(null) }

    LazyColumn {
        items(tablaturas) { tablatura ->
            TablaturaItem(tablatura)
        }
    }
}

@Composable
fun TablaturaItem(tablatura: Tablatura) {
    // Usando valores padrão caso os dados ainda possam ser nulos
    val titulo = tablatura.titulo ?: "Título padrão"
    val conteudo = tablatura.conteudo ?: "Conteúdo padrão"
    val caminhoImagem = tablatura.caminhoImagem ?: "imagem_padrao"

    Log.d("TablaturaItem", "Título: ${tablatura.titulo}, Conteúdo: ${tablatura.conteudo}, Imagem: ${tablatura.caminhoImagem}")

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val imageResource = caminhoImagem.toIntOrNull()
        if (imageResource != null) {
            Image(
                bitmap = ImageBitmap.imageResource(id = imageResource),
                contentDescription = titulo,
                modifier = Modifier.size(64.dp)
            )
        } else {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.img_tablatura3),
                contentDescription = "Imagem padrão",
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = titulo, style = MaterialTheme.typography.titleMedium)
            Text(text = conteudo, maxLines = 1, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
