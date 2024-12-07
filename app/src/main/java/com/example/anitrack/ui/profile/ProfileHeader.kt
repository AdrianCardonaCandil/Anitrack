package com.example.anitrack.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.anitrack.R

@Composable
fun ProfileHeader(
    profileImageUrl: String,
    userName: String,
    userId: String,
    joinedDate: String,
    description: String,
    onDeleteAccountClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onShareProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    isOwner: Boolean // NEW param
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            painter = if (profileImageUrl.isNotEmpty()) {
                rememberAsyncImagePainter(model = profileImageUrl)
            } else {
                painterResource(id = R.drawable.default_pfp)
            },
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(150.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            UserDetails(
                userName = userName,
                joinedDate = joinedDate,
                description = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileActions(
                onDeleteAccountClick = onDeleteAccountClick,
                userId = userId,
                onEditProfileClick = onEditProfileClick,
                onShareProfileClick = onShareProfileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
                isOwner = isOwner // pass down
            )
        }
    }
}
