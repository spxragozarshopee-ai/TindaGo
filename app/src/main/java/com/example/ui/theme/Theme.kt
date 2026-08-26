package com.example.ui.theme

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
  primary = MintPrimaryLight,
  onPrimary = Color.Black,
  primaryContainer = MintPrimaryDark,
  onPrimaryContainer = MintContainer,
  secondary = SoftSageLight,
  onSecondary = Color.Black,
  secondaryContainer = SoftSage,
  onSecondaryContainer = Color.White,
  tertiary = DealOrange,
  background = Color(0xFF121815),
  surface = Color(0xFF1E2621),
  onBackground = Color(0xFFE2E8F0),
  onSurface = Color(0xFFF1F5F9)
)

private val LightColorScheme = lightColorScheme(
  primary = MintPrimary,
  onPrimary = Color.White,
  primaryContainer = MintContainer,
  onPrimaryContainer = OnMintContainer,
  secondary = SoftSage,
  onSecondary = Color.White,
  secondaryContainer = SoftSageContainer,
  onSecondaryContainer = TextDark,
  tertiary = DealOrange,
  onTertiary = Color.White,
  tertiaryContainer = DealOrangeContainer,
  onTertiaryContainer = DealOrangeDark,
  background = LightBackground,
  onBackground = TextDark,
  surface = LightSurface,
  onSurface = TextDark,
  surfaceVariant = LightSurfaceVariant,
  onSurfaceVariant = TextMuted,
  outline = BorderLight
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // We use our signature Mint Green palette by default
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
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

