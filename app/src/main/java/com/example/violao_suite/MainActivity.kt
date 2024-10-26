package com.example.violao_suite

// Importação de pacotes contendo as funções das abas em module
import com.example.violao_suite.modules.AcordeDetalheScreen
import com.example.violao_suite.modules.AcordesScreen
import com.example.violao_suite.modules.AfinadorScreen
import com.example.violao_suite.modules.MetronomoScreen
import com.example.violao_suite.modules.TablaturasScreen

// Importando os pacotes padrões

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

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
        backgroundColor = Color(0xFF141218),
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        "tablaturas" -> Image(
                            painter = painterResource(
                                id = if (isSelected) R.drawable.ic_tablaturas_selecionado else R.drawable.ic_tablaturas
                            ),
                            contentDescription = "Tablaturas",
                            modifier = Modifier.size(32.dp)
                        )
                        "acordes" -> Image(
                            painter = painterResource(
                                id = if (isSelected) R.drawable.ic_acordes_selecionado else R.drawable.ic_acordes
                            ),
                            contentDescription = "Acordes",
                            modifier = Modifier.size(32.dp)
                        )
                        "metronomo" -> Image(
                            painter = painterResource(
                                id = if (isSelected) R.drawable.ic_metronomo_selecionado else R.drawable.ic_metronomo
                            ),
                            contentDescription = "Metronomo",
                            modifier = Modifier.size(32.dp)
                        )
                        "afinador" -> Image(
                            painter = painterResource(
                                id = if (isSelected) R.drawable.ic_afinador_selecionado else R.drawable.ic_afinador
                            ),
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
                selected = isSelected,
                selectedContentColor = Color(0xFFD0BCFE),
                unselectedContentColor = Color(0xFFCAC4D0),
                onClick = {
                    if (screen == "acordes" && currentRoute == "acorde_detalhe") {
                        // Se já estiver no acorde detalhado, voltar para a lista de acordes
                        navController.popBackStack("acordes", inclusive = false)
                    } else {
                        navController.navigate(screen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = "acordes") {

        // 1 - Acordes
        composable("acordes") { AcordesScreen(navController) }
        composable("acorde_detalhe/{acorde}") { backStackEntry ->
            val acorde = backStackEntry.arguments?.getString("acorde")
            AcordeDetalheScreen(acorde)
        }

        // 2 - Tablaturas
        composable("tablaturas") { TablaturasScreen() }

        // 3 - Metronomo
        composable("metronomo") {
            val context = LocalContext.current
            MetronomoScreen(context)
        }

        // 4 - Afinador
        composable("afinador") { AfinadorScreen() }
    }
}






