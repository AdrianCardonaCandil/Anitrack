package com.example.anitrack.ui.lists

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.anitrack.R

@Composable
fun ListsTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabTitles = listOf(
        stringResource(R.string.tab_watching),
        stringResource(R.string.tab_completed),
        stringResource(R.string.tab_plan_to_watch),
        stringResource(R.string.tab_loved)
    )

    TabRow(selectedTabIndex = selectedTabIndex) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(text = title) },
            )
        }
    }
}
