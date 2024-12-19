package com.example.wellnessapp.components.blogdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.wellnessapp.R
import com.example.wellnessapp.model.BlogPost
@Composable
fun BlogItem(
    blogPost: BlogPost,
    onClick: (String) -> Unit, // Navigate to BlogDetailsPage using blogId
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
            .clickable { onClick(blogPost.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFFFFFFF)
        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                // Display Image from Firestore
                if (blogPost.imageUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = blogPost.imageUrl), // Use the image URL
                        contentDescription = "Blog Thumbnail",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder), // Your placeholder
                        contentDescription = "Blog Thumbnail",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                // Display Blog Title and Subheading
                BlogInformation(blogPost.title, blogPost.subheading)

                // Display Author Information (Name, Date, Category)
                AuthorInformation(blogPost.authorName, blogPost.publicationDate, blogPost.category)
            }
        }
    }
}