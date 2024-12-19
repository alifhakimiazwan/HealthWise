package com.example.wellnessapp.components.blogdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wellnessapp.R
import com.example.wellnessapp.data.BlogPost

@Composable
fun BlogDetailsPage(blogPost: BlogPost, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set background color to white
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Back arrow button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 8.dp) // Add some padding below the icon
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack, // Use the correct back icon
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary // Set color of the icon
            )
        }

        Box(
            modifier = Modifier
                .height(275.dp)
                .width(330.dp)// Set size to make it larger square (300.dp x 300.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)// Add padding below the image

        ) {
            Image(
                painter = painterResource(id = R.drawable.gym), // Ensure you have biceps.jpg in res/drawable
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Use Crop to fill the Box
            )
        }


        AuthorInformation(
            authorName = blogPost.authorName,
            publicationDate = blogPost.publicationDate,
            category = blogPost.category,
            modifier = Modifier.padding(vertical = 8.dp) // Add some vertical padding if desired
        )

        Text(
            text = blogPost.title,
            fontSize = 50.sp,
            style = MaterialTheme.typography.displayLarge.copy(lineHeight = 40.sp), // Adjust line height here
            textAlign = TextAlign.Start, // Align title to the start
            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp) // Same horizontal padding as the image
        )
        // Displaying the blog description
        Text(
            text = blogPost.description,
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 28.sp),
            textAlign = TextAlign.Start, // Align description to the start
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp) // Same horizontal padding as the image
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewBlogDetailsPage() {
//    WellnessappTheme(darkTheme = false) {
//        val sampleBlogPost = BlogPost(
//            id = "1",
//            title = "How to grow your biceps?",
//            subheading = "Effective exercises to build stronger biceps",
//            authorName = "John Doe",
//            publicationDate = "2024-10-01",
//            category = "Fitness",
//            description = "In this blog, we delve into the anatomy of the bicep muscle and discuss various exercises specifically designed to target and strengthen it. From dumbbell curls to chin-ups, we cover techniques that will not only enhance muscle growth but also improve overall arm strength. You'll find tips on proper form, rep ranges, and how to incorporate these exercises into your weekly workout routine for maximum effectiveness."
//        )
//
//        // Calling the BlogDetailsPage with sample data
//        BlogDetailsPage(blogPost = sampleBlogPost, )
//    }
//}