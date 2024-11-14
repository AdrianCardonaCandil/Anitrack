package com.example.anitrack.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anitrack.ui.lists.ContentList
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit
) {
    Column(
    ) {
        // ProfileHeader ocupa el 30% de la pantalla
        ProfileHeader(
            profileImageUrl = "https://www.example.com/profile.jpg",
            userName = "John Doe",
            joinedDate = "January 2021",
            description = "I love watching anime and reading manga. I'm a big fan of Studio Ghibli.",
            onEditProfileClick = {},
            onShareProfileClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f) // Ocupa el 30% de la altura total
        )

        // ProfileListHeader ocupa aproximadamente el 10% de la pantalla y está justo debajo de ProfileHeader
        ProfileListHeader(
            contentCount = 10,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.05f)
        )

        // ContentList ocupa el 50% de la pantalla, justo debajo de ProfileListHeader
        ContentList(
            selectedTabIndex = 3,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
        )

        Button(
            onClick = { onSignOutClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Sign Out"
            )
        }
    }
}
