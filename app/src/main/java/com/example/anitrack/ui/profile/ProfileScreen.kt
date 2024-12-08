package com.example.anitrack.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.anitrack.R
import com.example.anitrack.model.User
import com.example.anitrack.network.DatabaseResult
import com.example.anitrack.ui.lists.ContentList

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: ProfileViewModel,
    onContentClicked: (Int) -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    isOwner: Boolean
) {
    val userProfileState by viewModel.userProfile.collectAsState()
    val userContentListState by viewModel.userContentList.collectAsState()

    var isEditProfileDialogOpen by remember { mutableStateOf(false) }
    var isShareDialogOpen by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

    val onProfilePictureChangeRequest: () -> Unit = {
        imagePickerLauncher.launch("image/*")
    }

    LaunchedEffect(userId) {
        viewModel.loadUserProfileAndFavorites(userId)
    }

    Column(modifier = modifier.fillMaxSize()) {
        when (val profileResult = userProfileState) {
            is DatabaseResult.Success -> {
                val user = profileResult.data
                user?.let { currentUser ->
                    ProfileHeader(
                        profileImageUrl = currentUser.profilePicture,
                        userName = currentUser.username,
                        joinedDate = currentUser.createdAt ?: stringResource(R.string.unknown_date),
                        description = currentUser.description,
                        onEditProfileClick = {
                            if (isOwner) {
                                isEditProfileDialogOpen = true
                            }
                        },
                        onShareProfileClick = {
                            isShareDialogOpen = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f),
                        isOwner = isOwner
                    )
                }
            }
            is DatabaseResult.Failure -> {
                Text(
                    text = "Failed to load profile: ${profileResult.error.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> Text(    text = stringResource(R.string.loading_profile), modifier = Modifier.padding(16.dp))
        }

        when (val contentResult = userContentListState) {
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
                    onContentClicked = onContentClicked,
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
        }

        if (isOwner) {
            Button(
                onClick = { onSignOutClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.sign_out))
            }
        }
    }

    val userData = (userProfileState as? DatabaseResult.Success<User?>)?.data
    if (isEditProfileDialogOpen && userData != null && isOwner) {
        EditProfileDialog(
            onDismissRequest = {
                isEditProfileDialogOpen = false
                selectedImageUri = null
            },
            onDeleteAccountClick = {
                isEditProfileDialogOpen = false
                onDeleteAccountClick()
            },
            userId = userData.id,
            currentUsername = userData.username,
            currentEmail = userData.email,
            currentDescription = userData.description,
            selectedImageUri = selectedImageUri,
            viewModel = viewModel,
            onProfilePictureChangeRequest = onProfilePictureChangeRequest
        )
    }

    if (isShareDialogOpen && userData != null) {
        ShareProfileDialog(
            userId = userData.id,
            onDismiss = { isShareDialogOpen = false }
        )
    }
}
