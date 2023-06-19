package com.debo.debo.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.debo.debo.features.core.presentation.components.AnimatableIcon
import com.debo.debo.util.Screen

enum class BottomNavigationItem(val icon: ImageVector, val screen: Screen, val routeName: String) {
    FEED(Icons.Default.Home, Screen.FeedScreen, "Home"),
    SEARCH(Icons.Default.Search, Screen.SearchScreen, "Search"),
    MESSAGES(Icons.Default.Message, Screen.MessagesScreen, "Messages"),
    PROFILE(Icons.Default.Person, Screen.ProfileScreen, "Profile")
}

@Composable
fun BottomNavBar(
    selectedItem: BottomNavigationItem, navController: NavController
) {
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surface)
            .shadow(3.dp)
            .padding(horizontal = 32.dp, vertical = 12.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (item in BottomNavigationItem.values()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AnimatableIcon(
                    imageVector = item.icon,
                    contentDescription = item.routeName,
                    size = 24.dp,
                    color = if (item == selectedItem) onSurfaceColor else onSurfaceVariant,
                    onClick = {
                        //animate navigation transition
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    })
                Spacer(modifier = Modifier.height(height = 4.dp))
                Text(
                    text = item.routeName,
                    color = if (item == selectedItem) onSurfaceColor else onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}