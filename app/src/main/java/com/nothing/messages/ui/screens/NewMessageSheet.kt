package com.nothing.messages.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nothing.messages.ui.theme.NothingColors
import com.nothing.messages.ui.theme.NothingType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMessageSheet(
    onDismiss: () -> Unit,
    onStart: (name: String, number: String) -> Unit
) {
    var toValue by remember { mutableStateOf("") }
    var nameValue by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(skipPartialExpansion = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = NothingColors.Bg,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(NothingColors.Red, Offset(0f, 0f), Offset(size.width, 0f), 2.dp.toPx())
                }
                .padding(20.dp)
                .navigationBarsPadding()
        ) {
            Text(
                text = "// NEW MESSAGE",
                style = NothingType.OsLabel.copy(letterSpacing = 2.sp)
            )
            Spacer(Modifier.height(18.dp))

            SheetField(label = "TO_", value = toValue, onValueChange = { toValue = it }, hint = "+1 (000) 000-0000")
            Spacer(Modifier.height(14.dp))
            SheetField(label = "NAME_ (OPTIONAL)", value = nameValue, onValueChange = { nameValue = it.uppercase() }, hint = "CONTACT NAME")

            Spacer(Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .border(1.dp, NothingColors.Gray)
                        .clickable { onDismiss() }
                ) {
                    Text("CANCEL", style = NothingType.ButtonLabel.copy(fontSize = 11.sp))
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(2f)
                        .height(48.dp)
                        .background(if (toValue.isNotBlank()) NothingColors.Red else NothingColors.Gray)
                        .clickable(enabled = toValue.isNotBlank()) { onStart(nameValue, toValue) }
                ) {
                    Text("START →", style = NothingType.ButtonLabel.copy(fontSize = 11.sp))
                }
            }
        }
    }
}

@Composable
private fun SheetField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String
) {
    Column {
        Text(text = label, style = NothingType.OsLabel.copy(letterSpacing = 2.sp))
        Spacer(Modifier.height(4.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = NothingType.InputText.copy(fontSize = 12.sp),
            cursorBrush = SolidColor(NothingColors.Red),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(NothingColors.Gray, Offset(0f, 0f), Offset(size.width, 0f), 1.dp.toPx())
                    drawLine(NothingColors.Gray, Offset(0f, 0f), Offset(0f, size.height), 1.dp.toPx())
                    drawLine(NothingColors.Gray, Offset(size.width, 0f), Offset(size.width, size.height), 1.dp.toPx())
                    val bottomColor = if (value.isNotEmpty()) NothingColors.Red else NothingColors.Gray
                    drawLine(bottomColor, Offset(0f, size.height), Offset(size.width, size.height), 2.dp.toPx())
                }
                .background(NothingColors.Dim)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            decorationBox = { inner ->
                Box {
                    if (value.isEmpty()) {
                        Text(hint, style = NothingType.InputText.copy(color = NothingColors.Gray, fontSize = 12.sp))
                    }
                    inner()
                }
            }
        )
    }
}
