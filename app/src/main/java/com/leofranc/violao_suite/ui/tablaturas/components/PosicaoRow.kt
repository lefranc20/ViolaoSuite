package com.leofranc.violao_suite.ui.tablaturas.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.leofranc.violao_suite.model.Posicao

@Composable
fun PosicaoRow(
    posicao: Posicao,
    onPosicaoChange: (Posicao) -> Unit,
    onDeletePosicao: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PosicaoField(posicao.corda1) { novaCorda ->
            onPosicaoChange(posicao.copy(corda1 = novaCorda))
        }
        Spacer(modifier = Modifier.width(4.dp))
        PosicaoField(posicao.corda2) { novaCorda ->
            onPosicaoChange(posicao.copy(corda2 = novaCorda))
        }
        Spacer(modifier = Modifier.width(4.dp))
        PosicaoField(posicao.corda3) { novaCorda ->
            onPosicaoChange(posicao.copy(corda3 = novaCorda))
        }
        Spacer(modifier = Modifier.width(4.dp))
        PosicaoField(posicao.corda4) { novaCorda ->
            onPosicaoChange(posicao.copy(corda4 = novaCorda))
        }
        Spacer(modifier = Modifier.width(4.dp))
        PosicaoField(posicao.corda5) { novaCorda ->
            onPosicaoChange(posicao.copy(corda5 = novaCorda))
        }
        Spacer(modifier = Modifier.width(4.dp))
        PosicaoField(posicao.corda6) { novaCorda ->
            onPosicaoChange(posicao.copy(corda6 = novaCorda))
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Botão para deletar a posição
        IconButton(onClick = { onDeletePosicao() }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Deletar Posição",
                tint = MaterialTheme.colors.error
            )
        }
    }
}

@Composable
fun PosicaoField(casa: Int, onValueChange: (Int) -> Unit) {
    TextField(
        value = casa.toString(),
        onValueChange = { novoValor -> onValueChange(novoValor.toIntOrNull() ?: 0) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier.width(50.dp)
    )
}
