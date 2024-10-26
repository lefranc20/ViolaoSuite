package com.example.violao_suite.modules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.violao_suite.R

@Composable
fun AfinadorScreen() {
    var afinadorEstado by remember { mutableStateOf("Parado") }
    var ouvindo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título que reflete o estado atual do afinador
        Text(
            text = "Afinador / $afinadorEstado",
            // color = Color(0xFF000000),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Icone do afinador (o visual do afinador em semicírculo)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(200.dp)
        ) {
            // Semi-círculo de afinação
            Image(
                painter = painterResource(id = R.drawable.img_afinador),
                contentDescription = "Semicírculo de Afinação",
                modifier = Modifier.fillMaxSize()
            )

            // Ícone de microfone ao centro
            Icon(
                painter = painterResource(id = R.drawable.img_microfone),
                contentDescription = "Microfone",
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        ouvindo = !ouvindo
                        afinadorEstado = if (ouvindo) "Escutando" else "Parado"
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de instrução que muda conforme o estado
        Text(
            text = if (ouvindo) "Ouvindo... pressione o botão novamente para parar." else "Pressione o botão e toque as notas do seu instrumento para verificar o afinamento.",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center
        )
    }
}