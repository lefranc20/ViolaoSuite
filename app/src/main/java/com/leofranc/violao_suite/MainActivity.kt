package com.leofranc.violao_suite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.leofranc.violao_suite.data.AppDatabase
import com.leofranc.violao_suite.repository.TablaturaRepository
import com.leofranc.violao_suite.ui.acordes.AcordesScreen
import com.leofranc.violao_suite.ui.acordes.AcordeDetalheScreen
import com.leofranc.violao_suite.ui.acordes.obterImagemResourceId
import com.leofranc.violao_suite.ui.metronomo.MetronomoScreen
import com.leofranc.violao_suite.ui.afinador.AfinadorScreen
import com.leofranc.violao_suite.ui.tablaturas.TablaturasScreen
import com.leofranc.violao_suite.ui.tablaturas.TablaturaEditorScreen
import com.leofranc.violao_suite.ui.theme.ViolaoSuiteTheme
import com.leofranc.violao_suite.ui.theme.CorAbaSelecionada
import com.leofranc.violao_suite.ui.theme.CorAbaNaoSelecionada
import com.leofranc.violao_suite.ui.theme.CorFundo
import com.leofranc.violao_suite.viewmodel.TablaturaViewModel
import com.leofranc.violao_suite.viewmodel.TablaturaViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val repository by lazy { TablaturaRepository(database.tablaturaDao()) }
    private val tablaturaViewModel: TablaturaViewModel by viewModels {
        TablaturaViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp(tablaturaViewModel)
        }
    }
}

@Composable
fun MainApp(tablaturaViewModel: TablaturaViewModel) {
    ViolaoSuiteTheme {  // Aplica o tema customizado
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            NavigationHost(navController, Modifier.padding(paddingValues), tablaturaViewModel)
        }
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
        backgroundColor = CorFundo,
        contentColor = Color.White
    ) {
        val currentRoute = currentRoute(navController)

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(32.dp)
                    )
                },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                selectedContentColor = CorAbaSelecionada,
                unselectedContentColor = CorAbaNaoSelecionada,
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
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    tablaturaViewModel: TablaturaViewModel
) {
    NavHost(navController, startDestination = "tablaturas", modifier = modifier) {

        // Rota para a lista de tablaturas
        composable("tablaturas") {
            TablaturasScreen(navController = navController, viewModel = tablaturaViewModel)
        }

        // Rota para o editor de tablaturas
        composable(
            route = "tablatura/{tablaturaId}",
            arguments = listOf(navArgument("tablaturaId") { type = NavType.LongType })
        ) { backStackEntry ->
            val tablaturaId = backStackEntry.arguments?.getLong("tablaturaId") ?: 0L
            TablaturaEditorScreen(
                navController = navController,
                viewModel = tablaturaViewModel,
                tablaturaId = tablaturaId
            )
        }

        // Rota para a lista de acordes
        composable("acordes") {
            AcordesScreen(navController = navController)
        }

        composable(
            route = "acorde_detalhe/{nome}/{imagem}",
            arguments = listOf(
                navArgument("nome") { type = NavType.StringType },
                navArgument("imagem") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nomeAcorde = backStackEntry.arguments?.getString("nome") ?: ""
            val imagem = backStackEntry.arguments?.getString("imagem") ?: ""
            val resourceID = obterImagemResourceId(context = LocalContext.current, nomeImagem = imagem)

            AcordeDetalheScreen(
                navController = navController,
                resourceID = resourceID,
                nomeAcorde = nomeAcorde
            )
        }



        // Rota para o metrônomo
        composable("metronomo") {
            MetronomoScreen(context = LocalContext.current)
        }

        // Rota para o afinador
        composable("afinador") {
            AfinadorScreen()
        }
    }
}

// Dados para os itens de navegação
data class BottomNavItem(val route: String, val icon: Int, val label: String)
