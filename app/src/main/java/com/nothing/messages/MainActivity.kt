package com.nothing.messages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.nothing.messages.data.MessagesViewModel
import com.nothing.messages.ui.screens.*
import com.nothing.messages.ui.theme.NothingColors
import com.nothing.messages.ui.theme.NothingMessagesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NothingMessagesTheme {
                NothingMessagesApp()
            }
        }
    }
}

@Composable
fun NothingMessagesApp() {
    val viewModel: MessagesViewModel = viewModel()
    val conversations by viewModel.conversations.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    var showNewMessage by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NothingColors.Bg)
            .systemBarsPadding()
    ) {
        NavHost(
            navController = navController,
            startDestination = "list",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(220))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it / 4 }, animationSpec = tween(220))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it / 4 }, animationSpec = tween(220))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(220))
            }
        ) {
            composable("list") {
                ConversationListScreen(
                    conversations = conversations,
                    onConversationClick = { id ->
                        viewModel.markRead(id)
                        navController.navigate("chat/$id")
                    },
                    onNewMessage = { showNewMessage = true },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(
                route = "chat/{convId}",
                arguments = listOf(navArgument("convId") { type = NavType.LongType })
            ) { backStackEntry ->
                val convId = backStackEntry.arguments?.getLong("convId") ?: return@composable
                val liveConv = conversations.find { it.id == convId }
                if (liveConv != null) {
                    ChatScreen(
                        conversation = liveConv,
                        onBack = { navController.popBackStack() },
                        onSend = { text -> viewModel.sendMessage(convId, text) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        if (showNewMessage) {
            NewMessageSheet(
                onDismiss = { showNewMessage = false },
                onStart = { name, number ->
                    viewModel.addConversation(name, number)
                    showNewMessage = false
                    val newId = viewModel.conversations.value.firstOrNull()?.id
                    if (newId != null) {
                        navController.navigate("chat/$newId")
                    }
                }
            )
        }
    }
}
