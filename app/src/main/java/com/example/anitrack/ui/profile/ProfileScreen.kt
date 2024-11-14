package com.example.anitrack.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anitrack.model.Content
import com.example.anitrack.model.User
import com.example.anitrack.network.DatabaseResult
import com.example.anitrack.ui.lists.ContentList

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onSignOutClick: () -> Unit
) {
    // Observe the user profile and content list state flows
    val userProfileState by viewModel.userProfile.collectAsState()
    val contentListState by viewModel.contentList.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {

        // Display the profile header only if user profile data is successfully loaded
        when (val profileResult = userProfileState) {
            is DatabaseResult.Success -> {
                val user = profileResult.data
                user?.let {
                    ProfileHeader(
                        profileImageUrl = user.profilePicture,
                        userName = user.username,
                        joinedDate = user.createdAt ?: "Unknown",
                        description = user.description,
                        onEditProfileClick = {},
                        onShareProfileClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                    )
                }
            }
            is DatabaseResult.Failure -> {
                Text(
                    text = "Failed to load profile: ${profileResult.error.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> Text(text = "Loading profile...", modifier = Modifier.padding(16.dp))
        }

        // Display the content list header and the actual content list if loaded
        when (val contentResult = contentListState) {
            is DatabaseResult.Success -> {
                val contentList = contentResult.data

                ProfileListHeader(
                    contentCount = contentList.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.05f)
                )

                ContentList(
                    contentList = contentList,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                )
            }
            is DatabaseResult.Failure -> {
                Text(
                    text = "Failed to load contents: ${contentResult.error.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> Text(text = "Loading contents...", modifier = Modifier.padding(16.dp))
        }

        // Sign Out Button
        Button(
            onClick = { onSignOutClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Sign Out")
        }
    }
}
