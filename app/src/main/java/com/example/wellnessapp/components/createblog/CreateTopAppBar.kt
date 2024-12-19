package com.example.wellnessapp.components.createblog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wellnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit // Lambda for back button action
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp) // Replace with `dimensionResource` if using dimens
            .background(Color(0xFFFFFFFF))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Back Button
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back), // Replace with your back icon resource
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Title
            Text(
                text = "Create a Post",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.size(48.dp)) // Placeholder for spacing
        }
    }
}
