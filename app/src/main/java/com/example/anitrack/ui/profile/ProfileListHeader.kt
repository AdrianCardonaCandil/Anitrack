package com.example.anitrack.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anitrack.R
import androidx.compose.ui.text.font.FontWeight
import com.example.anitrack.ui.theme.AppTypography

@Composable
fun ProfileListHeader(
    contentCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "Favorites Icon",
                tint = Color.Red,
                modifier = Modifier.size(24.dp).padding(top = 5.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Favorites",
                style = AppTypography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$contentCount",
                style = AppTypography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_bar_chart_24),
                contentDescription = "Content Icon",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
