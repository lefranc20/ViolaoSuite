package com.leofranc.violao_suite.modules

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.AudioFormat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

@Composable
fun AfinadorScreen() {
    val context = LocalContext.current
    val stringFrequencies = listOf(82.41, 110.00, 146.83, 196.00, 246.94, 329.63)
    var detectedFrequency by remember { mutableStateOf(0.0) }
    var closestNote by remember { mutableStateOf("") }
    var permissionGranted by remember { mutableStateOf(false) }

    // Solicitação de permissão
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }

    // Verifique a permissão e solicite-a se necessário
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_GRANTED
        ) {
            permissionGranted = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    // Somente inicia a detecção de frequência se a permissão for concedida
    if (permissionGranted) {
        LaunchedEffect(Unit) {
            detectFrequency { frequency ->
                detectedFrequency = frequency
                closestNote = getClosestString(frequency, stringFrequencies)
            }
        }
    }

    Column {
        Text(text = "Frequência Detectada: $detectedFrequency Hz")
        Text(text = "Nota mais próxima: $closestNote")
        if (!permissionGranted) {
            Text(text = "Aguardando permissão para acessar o microfone...")
        }
    }
}

private suspend fun detectFrequency(onFrequencyDetected: (Double) -> Unit) {
    val bufferSize = AudioRecord.getMinBufferSize(
        44100,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    try {
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            44100,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        val buffer = ShortArray(bufferSize)
        audioRecord.startRecording()

        while (true) {
            val readCount = audioRecord.read(buffer, 0, buffer.size)
            val frequency = calculateFrequency(buffer, readCount)
            onFrequencyDetected(frequency)

            // Delay para evitar travamento, ajustável conforme necessário
            delay(100)
        }
        audioRecord.stop()
        audioRecord.release()
    } catch (e: SecurityException) {
        // Lida com a exceção caso a permissão seja negada
    }
}

private fun calculateFrequency(buffer: ShortArray, readCount: Int): Double {
    var zeroCrossings = 0
    for (i in 0 until readCount - 1) {
        if (buffer[i] * buffer[i + 1] < 0) zeroCrossings++
    }
    return zeroCrossings * 44100.0 / readCount
}

private fun getClosestString(frequency: Double, stringFrequencies: List<Double>): String {
    return stringFrequencies.minByOrNull { Math.abs(it - frequency) }?.let { "Nota para ${it}Hz" } ?: "Indefinido"
}
