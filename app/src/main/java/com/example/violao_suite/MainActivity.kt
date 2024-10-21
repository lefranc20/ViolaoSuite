package com.example.violao_suite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavigationHost(navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("tablaturas", "acordes", "metronomo", "afinador")
    val currentRoute = currentRoute(navController)
    BottomNavigation(
        backgroundColor = Color(0xFF141218), // Cor de fundo da barra de navegação inferior
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        "tablaturas" -> Image(
                            painter = painterResource(id = R.drawable.ic_tablaturas),
                            contentDescription = "Tablaturas",
                            modifier = Modifier.size(32.dp)
                        )
                        "acordes" -> Image(
                            painter = painterResource(id = R.drawable.ic_acordes),
                            contentDescription = "Acordes",
                            modifier = Modifier.size(32.dp)
                        )
                        "metronomo" -> Image(
                            painter = painterResource(id = R.drawable.ic_metronomo),
                            contentDescription = "Metronomo",
                            modifier = Modifier.size(32.dp)
                        )
                        "afinador" -> Image(
                            painter = painterResource(id = R.drawable.ic_afinador),
                            contentDescription = "Afinador",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.replaceFirstChar { it.uppercase() },
                        fontSize = 11.sp,
                    )
                },
                selected = currentRoute == screen,
                selectedContentColor = Color(0xFFD0BCFE), // Cor quando o item está selecionado
                unselectedContentColor = Color(0xFFCAC4D0), // Cor quando o item não está selecionado
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = "acordes") {
        composable("acordes") { AcordesScreen() }
        composable("tablaturas") { TablaturasScreen() }
        composable("metronomo") { MetronomoScreen() }
        composable("afinador") { AfinadorScreen() }
    }
}

// Exibição da lista de acordes em uma grade
@Composable
fun AcordesScreen() {
    val acordes = listOf(
        Pair("A menor", R.drawable.img_am), // Substitua pelos IDs das suas imagens
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
            fontSize = 20.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // Define 3 colunas
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(acordes) { acorde ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = acorde.second),
                        contentDescription = acorde.first,
                        modifier = Modifier.size(64.dp)
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
fun TablaturasScreen() {
    Text(text = "Tela de Tablaturas", modifier = Modifier.fillMaxSize())
}

@Composable
fun MetronomoScreen() {
    Text(text = "Tela de Metrônomo", modifier = Modifier.fillMaxSize())
}

@Composable
fun AfinadorScreen() {
    Text(text = "Tela de Afinador", modifier = Modifier.fillMaxSize())
}
