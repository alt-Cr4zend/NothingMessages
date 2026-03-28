package com.nothing.messages.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nothing.messages.data.Conversation
import com.nothing.messages.data.Message
import com.nothing.messages.ui.theme.NothingColors
import com.nothing.messages.ui.theme.NothingType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    conversation: Conversation,
    onBack: () -> Unit,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(conversation.messages.size) {
        if (conversation.messages.isNotEmpty()) {
            listState.animateScrollToItem(conversation.messages.size - 1)
        }
    }

    LaunchedEffect(inputText) {
        if (inputText.isNotEmpty()) {
            isTyping = true
            delay(1200)
            isTyping = false
        } else {
            isTyping = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NothingColors.Bg)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            NothingColors.Gray,
                            Offset(0f, size.height),
                            Offset(size.width, size.height),
                            1.dp.toPx()
                        )
                    }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                ChatIconButton(label = "←", onClick = onBack)
                Spacer(Modifier.width(12.dp))
                NothingAvatar(initials = conversation.avatar, size = 36.dp)
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = conversation.name,
                        style = NothingType.ChatName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = if (isTyping) "TYPING..." else conversation.number,
                        style = NothingType.ChatNumber.copy(
                            color = if (isTyping) NothingColors.Red else NothingColors.Gray
                        )
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ChatIconButton(label = "☎", onClick = {})
                    ChatIconButton(label = "⋮", onClick = {})
                }
            }

            // Messages
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .border(1.dp, NothingColors.Dim)
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(text = "TODAY — SMS", style = NothingType.DateLabel)
                        }
                    }
                }
                items(conversation.messages, key = { it.id }) { msg ->
                    MessageBubble(message = msg)
                }
            }

            // Input
            NothingDivider()
            ChatInputBar(
                value = inputText,
                onValueChange = { inputText = it },
                onSend = {
                    if (inputText.isNotBlank()) {
                        onSend(inputText)
                        inputText = ""
                    }
                }
            )

            // Char counter
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NothingColors.Dim)
                    .padding(horizontal = 14.dp, vertical = 3.dp)
                    .navigationBarsPadding()
            ) {
                val len = inputText.length
                val type = if (len > 160) "MMS" else "SMS"
                val segments = if (len > 160) Math.ceil(len / 153.0).toInt() else 1
                val max = if (len > 160) segments * 153 else 160
                Text(text = "$len/$max · $type", style = NothingType.CharCount)
            }
        }

        ScanlineOverlay()
    }
}

@Composable
fun ChatIconButton(label: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(34.dp)
            .border(1.dp, NothingColors.Gray)
            .clickable { onClick() }
    ) {
        Text(text = label, style = NothingType.ConvName.copy(letterSpacing = 0.sp))
    }
}

@Composable
private fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .background(NothingColors.Dim)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .border(1.dp, NothingColors.Gray)
                .clickable { }
        ) {
            Text("+", style = NothingType.ConvName.copy(color = NothingColors.Gray, fontSize = 18.sp))
        }

        Spacer(Modifier.width(8.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = NothingType.InputText,
            cursorBrush = SolidColor(NothingColors.Red),
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 36.dp)
                .drawBehind {
                    drawLine(NothingColors.Gray, Offset(0f, 0f), Offset(size.width, 0f), 1.dp.toPx())
                    drawLine(NothingColors.Gray, Offset(0f, 0f), Offset(0f, size.height), 1.dp.toPx())
                    drawLine(NothingColors.Gray, Offset(size.width, 0f), Offset(size.width, size.height), 1.dp.toPx())
                    val bottomColor = if (value.isNotEmpty()) NothingColors.Red else NothingColors.Gray
                    drawLine(bottomColor, Offset(0f, size.height), Offset(size.width, size.height), 2.dp.toPx())
                }
                .background(NothingColors.Bg)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            decorationBox = { inner ->
                Box {
                    if (value.isEmpty()) {
                        Text("MESSAGE_", style = NothingType.InputText.copy(color = NothingColors.Gray))
                    }
                    inner()
                }
            }
        )

        Spacer(Modifier.width(8.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .background(if (value.isNotBlank()) NothingColors.Red else NothingColors.Gray)
                .clickable(enabled = value.isNotBlank()) { onSend() }
        ) {
            Text("▶", style = NothingType.ConvName.copy(fontSize = 14.sp, letterSpacing = 0.sp))
        }
    }
}

@Composable
private fun MessageBubble(message: Message) {
    Row(
        horizontalArrangement = if (message.fromMe) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 3.dp)
            .animateContentSize()
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(if (message.fromMe) NothingColors.Red else NothingColors.Dim)
                .border(1.dp, if (message.fromMe) NothingColors.Red else NothingColors.Gray)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                Text(text = message.text, style = NothingType.BubbleText)
                Spacer(Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (message.fromMe) Arrangement.End else Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = message.time,
                        style = NothingType.BubbleMeta.copy(
                            color = if (message.fromMe) NothingColors.White.copy(alpha = 0.55f)
                            else NothingColors.Gray
                        )
                    )
                    if (message.fromMe) {
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "✓✓",
                            style = NothingType.BubbleMeta.copy(
                                color = NothingColors.White.copy(alpha = 0.8f),
                                fontSize = 9.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
