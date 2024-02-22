/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.rickandmortyapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val rickAndMortyDarkColorScheme = darkColorScheme(
    primary = rickAndMortyDarkPrimary,
    onPrimary = rickAndMortyDarkOnPrimary,
    primaryContainer = rickAndMortyDarkPrimaryContainer,
    onPrimaryContainer = rickAndMortyDarkOnPrimaryContainer,
    inversePrimary = rickAndMortyDarkPrimaryInverse,
    secondary = rickAndMortyDarkSecondary,
    onSecondary = rickAndMortyDarkOnSecondary,
    secondaryContainer = rickAndMortyDarkSecondaryContainer,
    onSecondaryContainer = rickAndMortyDarkOnSecondaryContainer,
    tertiary = rickAndMortyDarkTertiary,
    onTertiary = rickAndMortyDarkOnTertiary,
    tertiaryContainer = rickAndMortyDarkTertiaryContainer,
    onTertiaryContainer = rickAndMortyDarkOnTertiaryContainer,
    error = rickAndMortyDarkError,
    onError = rickAndMortyDarkOnError,
    errorContainer = rickAndMortyDarkErrorContainer,
    onErrorContainer = rickAndMortyDarkOnErrorContainer,
    background = rickAndMortyDarkBackground,
    onBackground = rickAndMortyDarkOnBackground,
    surface = rickAndMortyDarkSurface,
    onSurface = rickAndMortyDarkOnSurface,
    inverseSurface = rickAndMortyDarkInverseSurface,
    inverseOnSurface = rickAndMortyDarkInverseOnSurface,
    surfaceVariant = rickAndMortyDarkSurfaceVariant,
    onSurfaceVariant = rickAndMortyDarkOnSurfaceVariant,
    outline = rickAndMortyDarkOutline
)

private val rickAndMortyLightColorScheme = lightColorScheme(
    primary = rickAndMortyLightPrimary,
    onPrimary = rickAndMortyLightOnPrimary,
    primaryContainer = rickAndMortyLightPrimaryContainer,
    onPrimaryContainer = rickAndMortyLightOnPrimaryContainer,
    inversePrimary = rickAndMortyLightPrimaryInverse,
    secondary = rickAndMortyLightSecondary,
    onSecondary = rickAndMortyLightOnSecondary,
    secondaryContainer = rickAndMortyLightSecondaryContainer,
    onSecondaryContainer = rickAndMortyLightOnSecondaryContainer,
    tertiary = rickAndMortyLightTertiary,
    onTertiary = rickAndMortyLightOnTertiary,
    tertiaryContainer = rickAndMortyLightTertiaryContainer,
    onTertiaryContainer = rickAndMortyLightOnTertiaryContainer,
    error = rickAndMortyLightError,
    onError = rickAndMortyLightOnError,
    errorContainer = rickAndMortyLightErrorContainer,
    onErrorContainer = rickAndMortyLightOnErrorContainer,
    background = rickAndMortyLightBackground,
    onBackground = rickAndMortyLightOnBackground,
    surface = rickAndMortyLightSurface,
    onSurface = rickAndMortyLightOnSurface,
    inverseSurface = rickAndMortyLightInverseSurface,
    inverseOnSurface = rickAndMortyLightInverseOnSurface,
    surfaceVariant = rickAndMortyLightSurfaceVariant,
    onSurfaceVariant = rickAndMortyLightOnSurfaceVariant,
    outline = rickAndMortyLightOutline
)


val LocalCardShape = staticCompositionLocalOf<Shape> { RoundedCornerShape(0.dp) }

object AppTheme {
    val cardShape: Shape
        @Composable
        @ReadOnlyComposable
        get() = LocalCardShape.current
}

private val LightColorPalette = RickAndMortyColors(
    isDark = false
)

private val DarkColorPalette = RickAndMortyColors(
    isDark = true
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val (colors, customColors) = when {
        darkTheme -> rickAndMortyDarkColorScheme to DarkColorPalette
        else -> rickAndMortyLightColorScheme to LightColorPalette
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
        ProvideRickAndMortyColors(customColors) {
            MaterialTheme(
                colorScheme = colors,
                typography = Typography,
                shapes = shapes,
                content = content
            )
        }
    }
}

@Composable
fun ProvideRickAndMortyColors(
    colors: RickAndMortyColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalRickAndMortyColors provides colorPalette, content = content)
}

@Stable
class RickAndMortyColors(
    isDark: Boolean
) {
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: RickAndMortyColors) {
        isDark = other.isDark
    }
}

private val LocalRickAndMortyColors = staticCompositionLocalOf<RickAndMortyColors> {
    error("No colors provided")
}
