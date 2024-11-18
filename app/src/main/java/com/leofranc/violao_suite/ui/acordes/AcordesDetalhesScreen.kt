package com.leofranc.violao_suite.ui.acordes

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AcordeDetalheScreen(
    navController: NavHostController,
    resourceID: Int,
    nomeAcorde: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(nomeAcorde, style = TextStyle(fontSize = 20.sp, color = Color.White)) },
                backgroundColor = Color(0xFF2A282E),
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (resourceID != 0) {
                    Image(
                        painter = painterResource(id = resourceID),
                        contentDescription = "Imagem do Acorde",
                        modifier = Modifier.size(200.dp)
                    )
                } else {
                    Text("Imagem não disponível", color = Color.Red)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = nomeAcorde,
                    style = TextStyle(fontSize = 18.sp, color = Color.White)
                )
            }
        }
    )
}
