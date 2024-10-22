package com.example.violao_suite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.violao_suite.ui.theme.ViolaoSuiteTheme

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
        composable("acordes") { AcordesScreen(navController) }
        composable("acorde_detalhe/{acorde}") { backStackEntry ->
            val acorde = backStackEntry.arguments?.getString("acorde")
            AcordeDetalheScreen(acorde)
        }
        composable("tablaturas") { TablaturasScreen() }
        composable("metronomo") { MetronomoScreen() }
        composable("afinador") { AfinadorScreen() }
    }
}

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
            fontSize = 20.sp
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
