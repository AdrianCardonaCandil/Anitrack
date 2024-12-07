package com.example.anitrack.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anitrack.ui.theme.AppTypography
import com.example.anitrack.R

@Composable
fun ProfileActions(
    userId: String,
    modifier: Modifier = Modifier,
    onDeleteAccountClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onShareProfileClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onEditProfileClick() },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Edit", style = AppTypography.labelMedium)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_24),
                    contentDescription = "Edit Profile"
                )
            }
        }

        Button(
            onClick = { onShareProfileClick() },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Share", style = AppTypography.labelMedium)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_share_qr_24),
                    contentDescription = "Share Profile"
                )
            }
        }
    }
}
