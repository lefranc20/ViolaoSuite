package com.example.violao_suite.modules

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.violao_suite.R

@Composable
fun AcordesScreen(navController: NavHostController) {
    val acordes = listOf(
        Pair("A menor", R.drawable.img_am),
        Pair("Em", R.drawable.img_em),
        Pair("C maior", R.drawable.img_c),
        Pair("D maior", R.drawable.img_d),
        Pair("D7", R.drawable.img_d7),
        Pair("D menor", R.drawable.img_dm)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Lista de Acordes",
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(acordes) { acorde ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("acorde_detalhe/${acorde.first}")
                        }
                ) {
                    Image(
                        painter = painterResource(id = acorde.second),
                        contentDescription = acorde.first,
                        modifier = Modifier.size(96.dp)
                    )
                    Text(
                        text = acorde.first,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AcordeDetalheScreen(acorde: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = when (acorde) {
                "A menor" -> R.drawable.img_am
                "Em" -> R.drawable.img_em
                "C maior" -> R.drawable.img_c
                "D maior" -> R.drawable.img_d
                "D7" -> R.drawable.img_d7
                "D menor" -> R.drawable.img_dm
                else -> R.drawable.ic_launcher_foreground // Fallback para evitar crashes
            }),
            contentDescription = acorde,
            modifier = Modifier.size(256.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = acorde ?: "Acorde Desconhecido",
            fontSize = 24.sp
        )
    }
}
