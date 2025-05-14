package com.openclassrooms.rebonnte.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = vertRebonnte,
    secondary = BleuRebonnte,
    tertiary = GrayDarkRebonnte,
    background = NoirPur,
    surface = GrayDarkRebonnte,
    onPrimary = NoirPur,
    onSecondary = NoirPur,
    onTertiary = BlancPur,
    onBackground = BlancPur,
    onSurface = BlancPur
)

private val LightColorScheme = lightColorScheme(
    primary = vertRebonnte,
    secondary = BleuRebonnte,
    tertiary = GrayLightRebonnte,
    background = BlancPur,
    surface = GrayLightRebonnte,
    onPrimary = BlancPur,
    onSecondary = BlancPur,
    onTertiary = NoirPur,
    onBackground = NoirPur,
    onSurface = NoirPur
)

@Composable
fun RebonnteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
