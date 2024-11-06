package com.leofranc.violao_suite

// Importação de bibliotecas gerais
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

// Importando arquivos do projeto
import com.leofranc.violao_suite.features.acordes.*
import com.leofranc.violao_suite.features.metronomo.*
import com.leofranc.violao_suite.features.afinador.*
import com.leofranc.violao_suite.features.tablaturas.TelaTablaturas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
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
            val iconId = when (screen) {
                "tablaturas" -> if (isSelected) R.drawable.ic_tablaturas_selecionado else R.drawable.ic_tablaturas
                "acordes" -> if (isSelected) R.drawable.ic_acordes_selecionado else R.drawable.ic_acordes
                "metronomo" -> if (isSelected) R.drawable.ic_metronomo_selecionado else R.drawable.ic_metronomo
                "afinador" -> if (isSelected) R.drawable.ic_afinador_selecionado else R.drawable.ic_afinador
                else -> R.drawable.ic_launcher_foreground
            }

            BottomNavigationItem(
                icon = {
                    Image(
                        painter = painterResource(id = iconId),
                        contentDescription = screen,
                        modifier = Modifier.size(32.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.replaceFirstChar { it.uppercase() },
                        fontSize = 11.sp
                    )
                },
                selected = isSelected,
                selectedContentColor = Color(0xFFD0BCFE),
                unselectedContentColor = Color(0xFFCAC4D0),
                onClick = {
                    if (screen == "acordes" && currentRoute == "acorde_detalhe/{acordeNome}") {
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
    NavHost(navController, startDestination = "tablaturas") {
        composable("tablaturas") { TelaTablaturas() }

        composable("acordes") { TelaAcordes(navController) }

        composable("acorde_detalhe/{acordeNome}") { backStackEntry ->
            val context = LocalContext.current
            val acordeNome = backStackEntry.arguments?.getString("acordeNome") ?: "Indefinido"
            val resourceID = obterImagemResourceId(context, acordeNome)

            AcordeDetalheScreen(
                resourceID = resourceID,
                nomeAcorde = acordeNome,
                onVoltarClick = { navController.popBackStack() }
            )
        }

        composable("metronomo") { TelaMetronomo(LocalContext.current) }

        composable("afinador") { TelaAfinador() }
    }
}
