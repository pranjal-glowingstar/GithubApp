package com.apps.githubapp.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.apps.githubapp.R

@Composable
fun SearchHeader(searchTextField: String, onValueChange: (String) -> Unit) {
    val context = LocalContext.current
    Text(
        text = context.getString(R.string.welcome),
        style = MaterialTheme.typography.headlineMedium
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchTextField,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = context.getString(R.string.enter),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}