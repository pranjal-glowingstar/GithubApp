package com.apps.githubapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.apps.githubapp.R
import com.apps.githubapp.common.AppUtils
import com.apps.githubapp.common.models.Repository

@Composable
fun RepositoryCard(repository: Repository) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RepositoryInfoRow(
                title = stringResource(R.string.repository_name),
                value = repository.name
            )
            RepositoryInfoRow(
                title = stringResource(R.string.description),
                value = repository.description ?: AppUtils.AppConstants.NO_INFO
            )
            RepositoryInfoRow(
                title = stringResource(R.string.number_of_stars),
                value = repository.stargazersCount.toString()
            )
            RepositoryInfoRow(
                title = stringResource(R.string.fork_count),
                value = repository.forksCount.toString()
            )
            RepositoryInfoRow(
                title = stringResource(R.string.default_branch),
                value = repository.defaultBranch
            )
        }
    }
}

@Composable
private fun RepositoryInfoRow(title: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall
        )
    }
}