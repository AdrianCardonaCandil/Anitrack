package com.example.anitrack.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anitrack.ui.theme.AppTypography

@Composable
fun ProfileActions(
    onEditProfileClick: () -> Unit,
    onShareProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onEditProfileClick,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Edit Profile", style = AppTypography.labelMedium)
        }

        Button(
            onClick = onShareProfileClick,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Share Profile", style = AppTypography.labelMedium)
        }
    }
}
