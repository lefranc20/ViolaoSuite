package com.leofranc.violao_suite.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leofranc.violao_suite.data.model.Secao
import com.leofranc.violao_suite.data.model.TablaturaData

@Composable
fun TablaturaEditor(
    secoes: List<Secao>,
    onNoteChange: (secaoPosicao: Int, cordaIndex: Int, novaCasa: Int) -> Unit,
    onNoteDelete: (secaoPosicao: Int, cordaIndex: Int) -> Unit
) {
    Column {
        secoes.forEachIndexed { secaoIndex, secao ->
            Text(text = "Seção ${secao.posicao}")
            secao.notas.forEachIndexed { cordaIndex, nota ->
                // Exibe a casa e permite edição
                Row {
                    Text(text = "Corda ${cordaIndex + 1}: ${nota.casa}")
                    Button(onClick = { onNoteChange(secaoIndex, cordaIndex, 3) }) { // Passa 3 diretamente como nova casa
                        Text("Alterar Casa")
                    }
                    Button(onClick = { onNoteDelete(secaoIndex, cordaIndex) }) {
                        Text("Deletar Nota")
                    }
                }
            }
        }
    }
}
