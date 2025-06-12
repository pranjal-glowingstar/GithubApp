package com.apps.assignment.presentation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apps.assignment.R
import com.apps.assignment.common.AppUtils
import com.apps.assignment.common.models.UserSummary

@Composable
fun GithubUserTile(user: UserSummary, onItemClicked: (UserSummary) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onItemClicked(user) }
            .border(width = 2.dp, color = MaterialTheme.colorScheme.outline)
            .padding(all = 8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = context.getString(R.string.user_name), style = MaterialTheme.typography.titleSmall)
            Text(text = user.login, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = AppUtils.AppConstants.EMPTY,
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
        )
    }
}