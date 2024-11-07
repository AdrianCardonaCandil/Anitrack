package com.example.anitrack.ui.lists

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anitrack.R
import com.example.anitrack.ui.theme.onPrimaryContainerLight

@Composable
fun ListHeader(selectedTabIndex: Int, itemCount: Int) {
    // Define header text based on selected tab index
    val headerText = when (selectedTabIndex) {
        0 -> stringResource(R.string.tab_watching_header)
        1 -> stringResource(R.string.tab_completed_header)
        2 -> stringResource(R.string.tab_plan_to_watch_header)
        3 -> stringResource(R.string.tab_loved_header)
        else -> ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = headerText,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Row for Count and Icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$itemCount",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = 8.dp),
                fontSize = 16.sp
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_bar_chart_24),
                contentDescription = "Statistics",
                modifier = Modifier.size(20.dp),
                tint =onPrimaryContainerLight
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.primary
    )
}
