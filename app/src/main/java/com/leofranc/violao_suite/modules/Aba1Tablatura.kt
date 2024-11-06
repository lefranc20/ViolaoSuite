package com.leofranc.violao_suite.modules

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leofranc.violao_suite.data.TablaturasRepository
import com.leofranc.violao_suite.data.model.TablaturaData
import com.leofranc.violao_suite.R

@Composable
fun TablaturasScreen(
    onTablaturaClick: (TablaturaData) -> Unit
) {
    val context = LocalContext.current
    val repository = remember { TablaturasRepository(context) }
    var tablaturas by remember { mutableStateOf(repository.loadAllTablaturas()) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Lista de Tablaturas",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            color = Color.Black
        )

        // Lista de tablaturas
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(tablaturas) { tablatura ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onTablaturaClick(tablatura) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_tablatura1),
                        contentDescription = tablatura.titulo,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = tablatura.titulo,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }

        // Botão para adicionar nova tablatura
        Button(
            onClick = { /* Lógica para adicionar uma nova tablatura */ },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Adicionar Tablatura")
        }
    }
}
