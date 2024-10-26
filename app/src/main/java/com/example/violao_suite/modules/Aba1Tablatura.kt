package com.example.violao_suite.modules

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.example.violao_suite.R

@Composable
fun TablaturasScreen() {
    val tablaturas = listOf(
        Pair("Tablatura 1", R.drawable.img_tablatura1), // substitua com seus recursos de imagem
        Pair("Tablatura 2", R.drawable.img_tablatura2), // substitua com seus recursos de imagem
        Pair("Título Editável", R.drawable.img_tablatura3) // substitua com seus recursos de imagem
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Lista de Tablaturas",
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            color = Color.Black
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(tablaturas) { tablatura ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {

                        }
                ) {
                    Image(
                        painter = painterResource(id = tablatura.second),
                        contentDescription = tablatura.first,
                        modifier = Modifier.size(96.dp)
                    )
                    Text(
                        text = tablatura.first,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de pesquisa com um ícone
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Pesquisar",
                tint = Color(0xFFDACDEB),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Buscar tablatura",
                fontSize = 16.sp,
                color = Color(0xFFDACDEB),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Adicionar Tablatura",
                tint = Color(0xFFDACDEB),
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        // Ação ao clicar no botão de adicionar
                    }
            )
        }
    }
}