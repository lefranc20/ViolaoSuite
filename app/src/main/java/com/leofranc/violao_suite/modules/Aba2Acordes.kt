package com.leofranc.violao_suite.modules

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leofranc.violao_suite.R
import java.io.InputStreamReader

data class Acorde(val nome: String, val imagem: String)

fun carregarAcordes(context: Context): List<Acorde> {
    return try {
        val inputStream = context.assets.open("acordes.json")
        val reader = InputStreamReader(inputStream)
        val acordeListType = object : TypeToken<List<Acorde>>() {}.type
        Gson().fromJson(reader, acordeListType)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

@SuppressLint("DiscouragedApi")
@DrawableRes
fun obterImagemResourceId(context: Context, nomeImagem: String): Int {
    val resourceId = context.resources.getIdentifier(nomeImagem, "drawable", context.packageName)
    return if (resourceId != 0) resourceId else R.drawable.ic_launcher_foreground
}

@Composable
fun AcordesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val acordes = remember { carregarAcordes(context) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(acordes) { acorde ->
            val imagemResId = obterImagemResourceId(context, acorde.imagem)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate("acorde_detalhe/${acorde.imagem}") }
            ) {
                Image(
                    painter = painterResource(id = imagemResId),
                    contentDescription = acorde.nome,
                    modifier = Modifier.size(96.dp)
                )
                Text(text = acorde.nome, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun AcordeDetalheScreen(resourceID: Int, nomeAcorde: String, onVoltarClick: () -> Unit) {
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
        Text(text = nomeAcorde, modifier = Modifier.padding(top = 16.dp))
        Button(onClick = onVoltarClick, modifier = Modifier.padding(top = 16.dp)) {
            Text("Voltar")
        }
    }
}
