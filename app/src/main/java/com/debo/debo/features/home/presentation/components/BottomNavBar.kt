package com.debo.debo.features.home.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
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


    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = onSurfaceVariant
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        for (item in BottomNavigationItem.values()) {
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.screen.route
                } == true,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

        }
    }
}