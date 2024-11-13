package com.leofranc.violao_suite.ui.afinador

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AfinadorScreen() {
    val context = LocalContext.current
    var isPermissionGranted by remember { mutableStateOf(false) }
    var isDetecting by remember { mutableStateOf(false) }

    // Verifica e solicita permissão para gravação de áudio
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_CODE_RECORD_AUDIO
            )
        } else {
            isPermissionGranted = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Afinador", fontSize = 20.sp, color = Color.White) },
                backgroundColor = Color(0xFF2A282E)
            )
        },
        content = {
            if (isPermissionGranted) {
                AfinadorComponent(isDetecting = isDetecting, onToggleDetecting = { isDetecting = !isDetecting })
            } else {
                Text(
                    text = "A permissão para gravação de áudio é necessária para o afinador.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}

@Composable
fun AfinadorComponent(isDetecting: Boolean, onToggleDetecting: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var afinacaoEstado by remember { mutableStateOf("") }
    var notaDetectada by remember { mutableStateOf("") }
    val afinacaoPadrao = listOf(82.41 to "E", 110.0 to "A", 146.83 to "D", 196.0 to "G", 246.94 to "B", 329.63 to "E")
    var dispatcher by remember { mutableStateOf<AudioDispatcher?>(null) }

    // Handler para detecção de pitch
    val handler = PitchDetectionHandler { res, _ ->
        val pitch = res.pitch
        if (pitch > 0) {
            val (frequenciaMaisProxima, nomeNota) = afinacaoPadrao.minByOrNull { Math.abs(it.first - pitch) } ?: (0.0 to "")
            val diferenca = pitch - frequenciaMaisProxima
            afinacaoEstado = when {
                diferenca < -1.0 -> "Grave"
                diferenca > 1.0 -> "Aguda"
                else -> "Afinada"
            }
            notaDetectada = nomeNota
        }
    }

    // Inicia ou para o dispatcher conforme o estado de detecção
    LaunchedEffect(isDetecting) {
        if (isDetecting) {
            dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0).apply {
                addAudioProcessor(PitchProcessor(PitchEstimationAlgorithm.YIN, 22050f, 1024, handler))
            }
            withContext(Dispatchers.IO) {
                dispatcher?.run()
            }
        } else {
            dispatcher?.stop()
        }
    }

    // Para o dispatcher ao sair da aba
    DisposableEffect(Unit) {
        onDispose {
            dispatcher?.stop()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Nota detectada: $notaDetectada",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            text = "Estado da afinação: $afinacaoEstado",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Icon(
            imageVector = when (afinacaoEstado) {
                "Grave" -> Icons.Default.ArrowDownward
                "Aguda" -> Icons.Default.ArrowUpward
                else -> Icons.Default.Check
            },
            contentDescription = "Estado da afinação",
            tint = when (afinacaoEstado) {
                "Grave" -> Color.Blue
                "Aguda" -> Color.Red
                else -> Color.Green
            },
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onToggleDetecting) {
            Text(if (isDetecting) "Parar" else "Iniciar")
        }
    }
}

const val REQUEST_CODE_RECORD_AUDIO = 1001
