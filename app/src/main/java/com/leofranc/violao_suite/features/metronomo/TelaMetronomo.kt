package com.leofranc.violao_suite.features.metronomo

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leofranc.violao_suite.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TelaMetronomo(context: Context) {
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
