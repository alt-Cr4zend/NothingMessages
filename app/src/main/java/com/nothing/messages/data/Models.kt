package com.nothing.messages.data

data class Message(
    val id: Long,
    val text: String,
    val fromMe: Boolean,
    val time: String,
    val type: MessageType = MessageType.SMS
)

enum class MessageType { SMS, MMS }

data class Conversation(
    val id: Long,
    val name: String,
    val number: String,
    val avatar: String,
    val unreadCount: Int = 0,
    val messages: List<Message> = emptyList()
) {
    val lastMessage: Message? get() = messages.lastOrNull()
}

object SampleData {
    val conversations = listOf(
        Conversation(
            id = 1, name = "EMMA WILSON", number = "+1 (917) 555-0182", avatar = "EW", unreadCount = 2,
            messages = listOf(
                Message(1, "hey are you coming tonight?", false, "21:14"),
                Message(2, "wouldn't miss it", true, "21:16"),
                Message(3, "bring snacks pls 🙏", false, "21:17"),
                Message(4, "what time does it start", false, "21:42"),
            )
        ),
        Conversation(
            id = 2, name = "DAD", number = "+1 (212) 555-0043", avatar = "D", unreadCount = 0,
            messages = listOf(
                Message(1, "Call me when you get a chance", false, "09:30"),
                Message(2, "Sure, calling in 10", true, "09:45"),
            )
        ),
        Conversation(
            id = 3, name = "WORK — SLACK", number = "+1 (415) 555-0098", avatar = "WS", unreadCount = 5,
            messages = listOf(
                Message(1, "Your verification code is 847291. Valid for 10 minutes.", false, "14:02"),
            )
        ),
        Conversation(
            id = 4, name = "JAMES PARK", number = "+1 (646) 555-0177", avatar = "JP", unreadCount = 0,
            messages = listOf(
                Message(1, "sick fit btw", false, "Yesterday"),
                Message(2, "lol ty", true, "Yesterday"),
            )
        ),
        Conversation(
            id = 5, name = "MOM", number = "+1 (212) 555-0021", avatar = "M", unreadCount = 1,
            messages = listOf(
                Message(1, "Did you eat today?", false, "12:00"),
                Message(2, "yes mom 😭", true, "12:05"),
                Message(3, "Good. I made soup, come by this weekend", false, "12:06"),
            )
        ),
    )
}
