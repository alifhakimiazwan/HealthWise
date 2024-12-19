package com.example.wellnessapp.components.blogdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wellnessapp.R

@Composable
fun AuthorInformation(
    authorName: String,
    publicationDate: String,
    category: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_extrasmall),
                end = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture on the left
        Image(
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_small_size))
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.profilepicture),
            contentDescription = null
        )

        // Author information on the right
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(R.dimen.padding_small))
                .weight(1f)
        ) {
            Text(
                text = authorName,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = publicationDate,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
        }

        // Category with heart icon
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {


            Text(
                text = category,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )

        }
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Default.FavoriteBorder, // Default heart icon
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(25.dp)
        )
    }
}