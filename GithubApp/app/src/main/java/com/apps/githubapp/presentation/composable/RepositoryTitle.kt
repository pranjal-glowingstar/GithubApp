package com.apps.githubapp.presentation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.apps.githubapp.R
import com.apps.githubapp.common.AppUtils
import com.apps.githubapp.common.models.Repository

@Composable
fun RepositoryTile(repository: Repository) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .border(width = 2.dp, color = MaterialTheme.colorScheme.outline)
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = context.getString(R.string.repository_name),
                style = MaterialTheme.typography.titleSmall
            )
            Text(text = repository.name, style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = context.getString(R.string.description),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = repository.description ?: AppUtils.AppConstants.NO_INFO,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = context.getString(R.string.number_of_stars),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = repository.stargazersCount.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = context.getString(R.string.fork_count),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = repository.forksCount.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = context.getString(R.string.default_branch),
                style = MaterialTheme.typography.titleSmall
            )
            Text(text = repository.defaultBranch, style = MaterialTheme.typography.bodySmall)
        }
    }
}