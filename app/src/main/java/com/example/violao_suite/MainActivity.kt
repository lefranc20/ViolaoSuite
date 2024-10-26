package com.app.violao_suite

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*

<<<<<<< HEAD
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
=======
//noinspection UsingMaterialAndMaterial3Libraries


// import androidx.compose.runtime.*

>>>>>>> 82edaf1 (Remoção de Erros e Typos)
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries,UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries,UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries,UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
<<<<<<< HEAD
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/* Importações não utilizadas ainda:
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.violao_suite.ui.theme.ViolaoSuiteTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
*/
=======
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

>>>>>>> 82edaf1 (Remoção de Erros e Typos)
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
            fontSize = 16.sp
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
                        modifier = Modifier.size(96.dp)
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
    val tablaturas = listOf(
        Pair("Tablatura 1", R.drawable.img_tablatura1), // substitua com seus recursos de imagem
        Pair("Tablatura 2", R.drawable.img_tablatura2), // substitua com seus recursos de imagem
        Pair("Título Editável", R.drawable.img_tablatura3) // substitua com seus recursos de imagem
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Lista de Tablaturas",
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            color = Color.Black
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(tablaturas) { tablatura ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {

                        }
                ) {
                    Image(
                        painter = painterResource(id = tablatura.second),
                        contentDescription = tablatura.first,
                        modifier = Modifier.size(96.dp)
                    )
                    Text(
                        text = tablatura.first,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de pesquisa com um ícone
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Pesquisar",
                tint = Color(0xFFDACDEB),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Buscar tablatura",
                fontSize = 16.sp,
                color = Color(0xFFDACDEB),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Adicionar Tablatura",
                tint = Color(0xFFDACDEB),
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        // Ação ao clicar no botão de adicionar
                    }
            )
        }
    }
}

@Composable
fun MetronomoScreen(context: Context) {
    var isPlaying by remember { mutableStateOf(false) }
    var bpm by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Inicializa o MediaPlayer apenas uma vez
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.metronomo_som) }

    // Função para calcular o intervalo de tempo com base no BPM
    fun bpmToDelay(bpm: Int): Long {
        return (60000 / bpm).toLong() // 60000ms = 1 minuto
    }

    // Função para tocar o som no ritmo do BPM
    fun startMetronome(bpm: Int) {
        scope.launch {
            while (isPlaying) {
                mediaPlayer.start()
                delay(bpmToDelay(bpm)) // Pausa até o próximo "click"
                mediaPlayer.pause()     // Pausa logo após o toque
                mediaPlayer.seekTo(0)   // Reinicia o som para o início
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isPlaying) "Metrônomo / Reproduzindo" else "Metrônomo / Pausado",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo de texto para o BPM
        TextField(
            value = bpm,
            onValueChange = { bpm = it },
            label = { Text("Digite o BPM") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de Reproduzir/Pausar
        Button(onClick = {
            if (bpm.isNotEmpty() && bpm.toIntOrNull() != null) {
                isPlaying = !isPlaying
                if (isPlaying) {
                    startMetronome(bpm.toInt())
                } else {
                    mediaPlayer.pause() // Pausa quando o metrônomo é interrompido
                }
            }
        }) {
            Text(text = if (isPlaying) "Pausar" else "Reproduzir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isPlaying) {
            Text(text = "Tocando a $bpm BPM", fontSize = 18.sp)
        }
    }

    // Limpeza do MediaPlayer quando o Composable é removido da tela
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}


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