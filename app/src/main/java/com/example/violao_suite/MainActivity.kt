package com.example.violao_suite

// import androidx.compose.material3.*
import androidx.compose.material.*

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.sp // Import para definir o tamanho da fonte
import androidx.compose.foundation.Image
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.violao_suite.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyApp() {
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
    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        "tablaturas" -> Image(
                            painter = painterResource(id = R.drawable.ic_tablaturas), // Substitua pelo ID da sua imagem
                            contentDescription = "Tablaturas",
                            modifier = Modifier
                                .size(30.dp) // Tamanho do ícone
                                .align(Alignment.CenterVertically) // Centraliza verticalmente
                        )
                        "acordes" -> Image(
                            painter = painterResource(id = R.drawable.ic_acordes), // Substitua pelo ID da sua imagem
                            contentDescription = "Acordes",
                            modifier = Modifier
                                .size(24.dp) // Tamanho do ícone
                                .align(Alignment.CenterVertically) // Centraliza verticalmente
                        )
                        "metronomo" -> Image(
                            painter = painterResource(id = R.drawable.ic_metronomo), // Substitua pelo ID da sua imagem
                            contentDescription = "Metrônomo",
                            modifier = Modifier
                                .size(26.dp) // Tamanho do ícone
                                .align(Alignment.CenterVertically) // Centraliza verticalmente
                        )
                        "afinador" -> Image(
                            painter = painterResource(id = R.drawable.ic_afinador), // Substitua pelo ID da sua imagem
                            contentDescription = "Afinador",
                            modifier = Modifier
                                .size(20.dp) // Tamanho do ícone
                                .align(Alignment.CenterVertically) // Centraliza verticalmentes
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.replaceFirstChar { it.uppercase() },
                        fontSize = 11.sp, // Altere o tamanho da fonte conforme necessário
                    )
                },
                selected = currentRoute == screen,
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

@Composable
fun AcordesScreen() {
    Text(text = "Tela de Acordes", modifier = Modifier.fillMaxSize())
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