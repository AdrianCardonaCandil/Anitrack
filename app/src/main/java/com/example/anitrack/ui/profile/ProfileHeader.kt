
package com.example.anitrack.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ProfileHeader(
    profileImageUrl: String,
    userName: String,
    joinedDate: String,
    description: String,
    onEditProfileClick: () -> Unit,
    onShareProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Imagen de perfil cuadrada con bordes redondeados, ocupa 100% de la altura disponible
        Image(
            painter = rememberImagePainter(data = profileImageUrl),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(0.8f)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            // UserDetails ocupa el 70% de la altura
            UserDetails(
                userName = userName,
                joinedDate = joinedDate,
                description = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ProfileActions ocupa el 30% de la altura
            ProfileActions(
                onEditProfileClick = onEditProfileClick,
                onShareProfileClick = onShareProfileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            )
        }
    }
}
