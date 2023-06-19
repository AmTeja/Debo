package com.debo.debo.features.profile.presentation.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.debo.debo.features.home.presentation.components.BottomNavBar
import com.debo.debo.features.home.presentation.components.BottomNavigationItem

@Composable
fun ProfileError(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = BottomNavigationItem.PROFILE, navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Failed to load profile")
        }
    }
}