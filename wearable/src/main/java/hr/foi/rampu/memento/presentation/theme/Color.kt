package hr.foi.rampu.memento.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val MementoBlue = Color(0xFFCAE2FF)
val MementoDarkerBlue = Color(0xFFAEC8FC)
val MementoAccent = Color(0xFFFF5722)
val Red400 = Color(0xFFCF6679)

internal val wearColorPalette: Colors = Colors(
    primary = MementoBlue,
    primaryVariant = MementoDarkerBlue,
    secondary = MementoAccent,
    secondaryVariant = MementoAccent,
    error = Red400,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black
)