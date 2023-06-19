package com.debo.debo.features.messages.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.debo.debo.features.home.presentation.components.BottomNavBar
import com.debo.debo.features.home.presentation.components.BottomNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavBar(BottomNavigationItem.MESSAGES, navController) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = "Messages Screen")
        }
    }
}