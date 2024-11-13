package com.leofranc.violao_suite.ui.metronomo

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leofranc.violao_suite.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MetronomoScreen(context: Context) {
    var isPlaying by remember { mutableStateOf(false) }
    var bpm by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Inicializa o MediaPlayer uma única vez
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.metronomo_som) }

    // Calcula o intervalo de tempo com base no BPM
    fun bpmToDelay(bpm: Int): Long = (60000 / bpm).toLong()

    // Toca o som no ritmo do BPM
    fun startMetronome(bpm: Int) {
        scope.launch {
            while (isPlaying) {
                mediaPlayer.start()
                delay(bpmToDelay(bpm))
                mediaPlayer.pause()
                mediaPlayer.seekTo(0)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Metrônomo", fontSize = 20.sp, color = Color.White) },
                backgroundColor = Color(0xFF2A282E)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isPlaying) "Reproduzindo" else "Pausado",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Campo de entrada para o BPM
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
                            mediaPlayer.pause()
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
        }
    )

    // Libera o MediaPlayer ao sair da tela
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
