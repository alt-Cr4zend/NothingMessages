package com.nothing.messages.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MessagesViewModel : ViewModel() {

    private val _conversations = MutableStateFlow(SampleData.conversations)
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    fun sendMessage(convId: Long, text: String) {
        if (text.isBlank()) return
        val now = java.util.Calendar.getInstance()
        val time = String.format("%02d:%02d", now.get(java.util.Calendar.HOUR_OF_DAY), now.get(java.util.Calendar.MINUTE))
        val newMsg = Message(
            id = System.currentTimeMillis(),
            text = text,
            fromMe = true,
            time = time
        )
        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == convId) conv.copy(messages = conv.messages + newMsg)
                else conv
            }
        }
    }

    fun markRead(convId: Long) {
        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == convId) conv.copy(unreadCount = 0)
                else conv
            }
        }
    }

    fun addConversation(name: String, number: String) {
        val initials = if (name.isNotBlank()) {
            name.trim().split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString("")
        } else {
            number.filter { it.isDigit() }.takeLast(4).take(2)
        }
        val newConv = Conversation(
            id = System.currentTimeMillis(),
            name = name.ifBlank { number }.uppercase(),
            number = number,
            avatar = initials.ifEmpty { "?" },
            unreadCount = 0,
            messages = emptyList()
        )
        _conversations.update { listOf(newConv) + it }
    }

    fun getConversation(id: Long): Conversation? =
        _conversations.value.find { it.id == id }
}
