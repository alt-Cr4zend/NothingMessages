package com.nothing.messages.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nothing.messages.ui.theme.NothingColors
import com.nothing.messages.ui.theme.NothingType

// ── NothingAvatar ────────────────────────────────────────────────
@Composable
fun NothingAvatar(
    initials: String,
    size: Dp = 42.dp,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .border(1.dp, NothingColors.Gray)
            .background(Color.Transparent)
            .drawBehind {
                // Shadow/offset border
                drawRect(
                    color = NothingColors.Gray.copy(alpha = 0.25f),
                    topLeft = Offset(3.dp.toPx(), 3.dp.toPx()),
                    size = this.size
                )
            }
    ) {
        Text(
            text = initials,
            style = NothingType.AvatarText.copy(fontSize = (size.value * 0.3f).sp)
        )
    }
}

// ── UnreadBadge ──────────────────────────────────────────────────
@Composable
fun UnreadBadge(count: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(16.dp)
            .background(NothingColors.Red)
    ) {
        Text(
            text = count.toString(),
            style = NothingType.BubbleMeta.copy(
                fontSize = 8.sp,
                color = NothingColors.White,
                letterSpacing = 0.sp
            )
        )
    }
}

// ── ScanlineOverlay ──────────────────────────────────────────────
@Composable
fun ScanlineOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val lineHeight = 4.dp.toPx()
                var y = 0f
                while (y < size.height) {
                    drawRect(
                        color = Color.Black.copy(alpha = 0.04f),
                        topLeft = Offset(0f, y + lineHeight / 2),
                        size = androidx.compose.ui.geometry.Size(size.width, lineHeight / 2)
                    )
                    y += lineHeight
                }
            }
    )
}

// ── Corner accent decoration ─────────────────────────────────────
@Composable
fun CornerAccents(
    color: Color = NothingColors.Gray,
    size: Dp = 12.dp,
    thickness: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Top-left
        Box(Modifier.align(Alignment.TopStart)) {
            Box(Modifier.width(size).height(thickness).background(color))
            Box(Modifier.width(thickness).height(size).background(color))
        }
        // Top-right
        Box(Modifier.align(Alignment.TopEnd)) {
            Box(Modifier.width(size).height(thickness).background(color).align(Alignment.TopEnd))
            Box(Modifier.width(thickness).height(size).background(color).align(Alignment.TopEnd))
        }
        // Bottom-left
        Box(Modifier.align(Alignment.BottomStart)) {
            Box(Modifier.width(size).height(thickness).background(color).align(Alignment.BottomStart))
            Box(Modifier.width(thickness).height(size).background(color).align(Alignment.BottomStart))
        }
        // Bottom-right
        Box(Modifier.align(Alignment.BottomEnd)) {
            Box(Modifier.width(size).height(thickness).background(color).align(Alignment.BottomEnd))
            Box(Modifier.width(thickness).height(size).background(color).align(Alignment.BottomEnd))
        }
    }
}

// ── Divider line ─────────────────────────────────────────────────
@Composable
fun NothingDivider(color: Color = NothingColors.Gray, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color)
    )
}
