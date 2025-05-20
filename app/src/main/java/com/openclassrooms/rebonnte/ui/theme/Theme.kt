package com.openclassrooms.rebonnte.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
    onPrimary = NoirPur,
    secondary = BleuRebonnte,
    onSecondary = NoirPur,
    tertiary = GrayLightRebonnte,
    onTertiary = NoirPur,
    background = BlancPur,
    onBackground = NoirPur,
    surface = Color(0xFFF4F4F4),
    onSurface = NoirPur
)

@Composable
fun RebonnteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
