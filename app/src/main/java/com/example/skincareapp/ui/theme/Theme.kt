package com.example.skincareapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SkinCareColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = White,
    secondary = DarkGreen,
    background = BackgroundCream,
    onBackground = TextPrimary,
    surface = White,
    onSurface = TextPrimary,
    surfaceVariant = LightGreen,
    onSurfaceVariant = TextSecondary
)

@Composable
fun SkinCareAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SkinCareColorScheme,
        content = content
    )
}