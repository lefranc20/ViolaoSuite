package com.leofranc.violao_suite

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*


// Importando telas do projeto
import com.leofranc.violao_suite.features.acordes.TelaAcordes
import com.leofranc.violao_suite.features.metronomo.TelaMetronomo
import com.leofranc.violao_suite.features.afinador.TelaAfinador
import com.leofranc.violao_suite.features.tablaturas.TelaTablaturas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Aplicativo iniciado")
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavigationHost(navController, Modifier.padding(paddingValues))
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("tablaturas", R.drawable.ic_tablaturas, "Tablaturas"),
        BottomNavItem("acordes", R.drawable.ic_acordes, "Acordes"),
        BottomNavItem("metronomo", R.drawable.ic_metronomo, "Metrônomo"),
        BottomNavItem("afinador", R.drawable.ic_afinador, "Afinador")
    )

    BottomNavigation(
        backgroundColor = Color(0xFF141218),
        contentColor = Color.White
    ) {
        val currentRoute = currentRoute(navController)

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(32.dp) // Define o tamanho do ícone aqui
                    )
                },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                selectedContentColor = Color(0xFFD0BCFE),
                unselectedContentColor = Color(0xFFCAC4D0),
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
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
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "tablaturas", modifier = modifier) {
        composable("tablaturas") { TelaTablaturas() }
        composable("acordes") { TelaAcordes(navController) }
        composable("metronomo") { TelaMetronomo(LocalContext.current) }
        composable("afinador") { TelaAfinador() }
    }
}


// Dados para os itens de navegação
data class BottomNavItem(val route: String, val icon: Int, val label: String)
