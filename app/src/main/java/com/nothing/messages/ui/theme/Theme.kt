package com.nothing.messages.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Nothing Palette ──────────────────────────────────────────────
object NothingColors {
    val Red       = Color(0xFFFF3B30)
    val White     = Color(0xFFFFFFFF)
    val Gray      = Color(0xFF3A3A3A)
    val DimGray   = Color(0xFF888888)
    val Dim       = Color(0xFF1A1A1A)
    val Bg        = Color(0xFF0A0A0A)
    val DeepBg    = Color(0xFF050505)
    val Unread    = Color(0xFF888888)
}

// ── Monospace font family (system fallback — works without assets) ─
val NothingFontFamily = FontFamily.Monospace

// ── Nothing text styles ──────────────────────────────────────────
object NothingType {
    val OsLabel = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 9.sp,
        letterSpacing = 0.22.sp,
        color = NothingColors.Gray
    )
    val ScreenTitle = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 26.sp,
        letterSpacing = 1.3.sp,
        color = NothingColors.White
    )
    val ConvName = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 12.sp,
        letterSpacing = 1.44.sp,
        color = NothingColors.White
    )
    val ConvPreview = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 10.sp,
        letterSpacing = 0.5.sp,
        color = NothingColors.Gray
    )
    val TimeStamp = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 10.sp,
        letterSpacing = 0.5.sp,
        color = NothingColors.Gray
    )
    val BubbleText = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 12.sp,
        letterSpacing = 0.6.sp,
        color = NothingColors.White,
        lineHeight = 18.sp
    )
    val BubbleMeta = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 9.sp,
        letterSpacing = 0.9.sp,
        color = NothingColors.Gray
    )
    val ButtonLabel = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 12.sp,
        letterSpacing = 2.4.sp,
        color = NothingColors.White
    )
    val InputText = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 12.sp,
        letterSpacing = 0.96.sp,
        color = NothingColors.White
    )
    val CharCount = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 9.sp,
        letterSpacing = 0.9.sp,
        color = NothingColors.Gray
    )
    val DateLabel = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 9.sp,
        letterSpacing = 1.8.sp,
        color = NothingColors.Gray
    )
    val AvatarText = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 13.sp,
        letterSpacing = 0.65.sp,
        color = NothingColors.White
    )
    val ChatName = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 12.sp,
        letterSpacing = 1.44.sp,
        color = NothingColors.White
    )
    val ChatNumber = TextStyle(
        fontFamily = NothingFontFamily,
        fontSize = 9.sp,
        letterSpacing = 0.9.sp,
        color = NothingColors.Gray
    )
}

private val NothingColorScheme = darkColorScheme(
    primary         = NothingColors.Red,
    onPrimary       = NothingColors.White,
    background      = NothingColors.Bg,
    onBackground    = NothingColors.White,
    surface         = NothingColors.Dim,
    onSurface       = NothingColors.White,
    outline         = NothingColors.Gray,
)

@Composable
fun NothingMessagesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NothingColorScheme,
        content = content
    )
}
