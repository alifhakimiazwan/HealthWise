package com.example.wellnessapp.components.blogdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.wellnessapp.R

@Composable
fun BlogInformation(
    blogTitle: String,
    blogSubheading: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = dimensionResource(R.dimen.padding_extrasmall), end = dimensionResource(
        R.dimen.padding_medium), start = dimensionResource(R.dimen.padding_medium), bottom = dimensionResource(
        R.dimen.padding_medium)
    )) {
        // Blog title
        Text(
            text = blogTitle,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_extrasmall)),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        // Blog subheading
        Text(
            text = blogSubheading,
            style = MaterialTheme.typography.bodySmall
        )
    }
}