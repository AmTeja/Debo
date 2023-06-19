package com.debo.debo.features.profile.presentation.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.debo.debo.features.home.presentation.components.BottomNavBar
import com.debo.debo.features.home.presentation.components.BottomNavigationItem

@Composable
fun ProfileLoading(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = BottomNavigationItem.PROFILE, navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}