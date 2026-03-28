package com.nothing.messages.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nothing.messages.data.Conversation
import com.nothing.messages.ui.theme.NothingColors
import com.nothing.messages.ui.theme.NothingType

@Composable
fun ConversationListScreen(
    conversations: List<Conversation>,
    onConversationClick: (Long) -> Unit,
    onNewMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchFocused by remember { mutableStateOf(false) }
    var filterUnread by remember { mutableStateOf(false) }

    val filtered = remember(conversations, searchQuery, filterUnread) {
        conversations
            .filter { if (filterUnread) it.unreadCount > 0 else true }
            .filter {
                searchQuery.isEmpty() ||
                    it.name.contains(searchQuery, ignoreCase = true) ||
                    it.number.contains(searchQuery)
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NothingColors.Bg)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = NothingColors.Gray,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(text = "NOTHING_OS // MESSAGES", style = NothingType.OsLabel)
                        Spacer(Modifier.height(4.dp))
                        Text(text = "INBOX", style = NothingType.ScreenTitle)
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .border(1.dp, if (filterUnread) NothingColors.Red else NothingColors.Gray)
                            .clickable { filterUnread = !filterUnread }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (filterUnread) "UNREAD ▲" else "FILTER",
                            style = NothingType.OsLabel.copy(
                                color = if (filterUnread) NothingColors.Red else NothingColors.Gray,
                                letterSpacing = 1.8.sp
                            )
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Search field
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    textStyle = NothingType.InputText.copy(fontSize = 12.sp),
                    cursorBrush = SolidColor(NothingColors.Red),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(NothingColors.Gray, Offset(0f, 0f), Offset(size.width, 0f), 1.dp.toPx())
                            drawLine(NothingColors.Gray, Offset(0f, 0f), Offset(0f, size.height), 1.dp.toPx())
                            drawLine(NothingColors.Gray, Offset(size.width, 0f), Offset(size.width, size.height), 1.dp.toPx())
                            val bottomColor = if (searchFocused || searchQuery.isNotEmpty()) NothingColors.Red else NothingColors.Gray
                            drawLine(bottomColor, Offset(0f, size.height), Offset(size.width, size.height), 2.dp.toPx())
                        }
                        .background(NothingColors.Dim)
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .onFocusChanged { searchFocused = it.isFocused },
                    decorationBox = { inner ->
                        Box {
                            if (searchQuery.isEmpty()) {
                                Text("SEARCH_", style = NothingType.InputText.copy(color = NothingColors.Gray, fontSize = 12.sp))
                            }
                            inner()
                        }
                    }
                )
            }

            // List
            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (filterUnread) "NO UNREAD MESSAGES" else "NO MESSAGES",
                        style = NothingType.OsLabel.copy(letterSpacing = 2.sp)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(filtered, key = { it.id }) { conv ->
                        ConversationItem(conversation = conv, onClick = { onConversationClick(conv.id) })
                    }
                }
            }

            // Compose button
            NothingDivider()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NothingColors.Bg)
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(NothingColors.Red)
                        .clickable { onNewMessage() }
                ) {
                    Text(text = "+   NEW MESSAGE", style = NothingType.ButtonLabel)
                }
            }
        }

        ScanlineOverlay()
    }
}

@Composable
private fun ConversationItem(conversation: Conversation, onClick: () -> Unit) {
    val last = conversation.lastMessage

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .drawBehind {
                if (conversation.unreadCount > 0) {
                    drawLine(NothingColors.Red, Offset(0f, 0f), Offset(0f, size.height), 2.dp.toPx())
                }
                drawLine(NothingColors.Dim, Offset(0f, size.height), Offset(size.width, size.height), 1.dp.toPx())
            }
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Box {
            NothingAvatar(initials = conversation.avatar)
            if (conversation.unreadCount > 0) {
                Box(modifier = Modifier.align(Alignment.TopEnd).offset(4.dp, (-4).dp)) {
                    UnreadBadge(conversation.unreadCount)
                }
            }
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = conversation.name,
                    style = NothingType.ConvName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Text(text = last?.time ?: "", style = NothingType.TimeStamp)
            }
            Spacer(Modifier.height(4.dp))
            if (last != null) {
                val preview = if (last.fromMe) "YOU: ${last.text}" else last.text
                Text(
                    text = preview,
                    style = NothingType.ConvPreview.copy(
                        color = if (conversation.unreadCount > 0) NothingColors.DimGray else NothingColors.Gray
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
