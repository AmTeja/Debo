package com.debo.debo.features.profile.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.debo.debo.features.core.presentation.components.ProfileAvatar
import com.debo.debo.features.home.presentation.components.BottomNavBar
import com.debo.debo.features.home.presentation.components.BottomNavigationItem
import com.debo.debo.features.profile.domain.model.Profile

@Composable
fun ProfilePage(profile: Profile, navController: NavController) {

    val scrollState = rememberScrollState()


    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = BottomNavigationItem.PROFILE, navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        enabled = true,
                        state = scrollState,
                        reverseScrolling = false
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = profile.name, style = MaterialTheme.typography.displaySmall)
                Text(text = "@${profile.userName}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                ProfileAvatar(imageUrl = profile.imageUrl, size = null)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = profile.bio, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Card(
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Followers",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "${profile.followers.size}",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Card(
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Following",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "${profile.following.size}",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

    }
}