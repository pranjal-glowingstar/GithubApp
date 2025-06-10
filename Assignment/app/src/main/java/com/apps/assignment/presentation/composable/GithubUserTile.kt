package com.apps.assignment.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apps.assignment.common.AppConstants
import com.apps.assignment.models.UserSummary

@Composable
fun GithubUserTile(user: UserSummary) {
    Row(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        Text(text = user.login)
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(model = user.avatarUrl, contentDescription = AppConstants.EMPTY, modifier = Modifier.size(90.dp).clip(CircleShape))
    }
}