// ui/theme/Theme.kt
package com.leofranc.violao_suite.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = CorAbaSelecionada,
    onPrimary = Color.White,
    background = CorFundo,
    surface = CorFundo,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun ViolaoSuiteTheme(content: @Composable () -> Unit) {
    val colors = DarkColorPalette // Apenas tema escuro configurado

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}