package com.example.violao_suite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("acordes", "tablaturas", "metronomo", "afinador")
    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        "acordes" -> Image(
                            painter = painterResource(id = R.drawable.ic_acordes), // Substitua pelo ID da sua imagem
                            contentDescription = "Acordes"
                        )
                        "tablaturas" -> Image(
                            painter = painterResource(id = R.drawable.ic_tablaturas), // Substitua pelo ID da sua imagem
                            contentDescription = "Tablaturas"
                        )
                        "metronomo" -> Image(
                            painter = painterResource(id = R.drawable.ic_metronomo), // Substitua pelo ID da sua imagem
                            contentDescription = "Metrônomo"
                        )
                        "afinador" -> Image(
                            painter = painterResource(id = R.drawable.ic_afinador), // Substitua pelo ID da sua imagem
                            contentDescription = "Afinador"
                        )
                    }
                },
                label = { Text(screen.capitalize()) },
                selected = false, // Aqui você pode adicionar lógica para definir o estado de seleção
                onClick = {
                    navController.navigate(screen)
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