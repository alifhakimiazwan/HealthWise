package com.example.wellnessapp.components.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wellnessapp.R
import com.example.wellnessapp.components.blogdetails.BlogInformation
import com.example.wellnessapp.components.createblog.BlogPostViewModel
import com.example.wellnessapp.ui.themeTheme.kt.WellnessappTheme
import com.example.wellnessapp.components.home.WellnessBottomAppBar
import com.example.wellnessapp.data.blogs
import com.google.firebase.auth.FirebaseAuth
import com.example.wellnessapp.model.BlogPost


@Composable
fun ProfileScreen(navController: NavController, viewModel: BlogPostViewModel = hiltViewModel()) {
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "Unknown User"
    val userEmail = user?.email ?: "No email found"

    val blogPosts by viewModel.blogPosts.collectAsState()
    val isLoading = blogPosts.isEmpty()
    val settingsIcon = R.drawable.icon_settings // Define the icon resource here
    val favoriteIcon = R.drawable.icon_favorite

    LaunchedEffect(Unit) {
        Log.d("ProfileScreen", "Fetching user blog posts...")
        viewModel.fetchUserBlogPosts(userEmail) // Trigger the fetch function based on the email or authorId
    }

    Scaffold(
        topBar = { ProfileTopAppBar() },
        bottomBar = { WellnessBottomAppBar(navController = navController, modifier = Modifier.fillMaxWidth()) },
        containerColor = Color.White // Scaffold background color
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues), // Ensure padding does not affect the white background
            color = Color.White // Set Surface background to white
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White) // Ensure the column has a white background
                    .padding(16.dp)
            ) {
                // Profile Picture and Info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    // Profile Picture
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.size(100.dp),
                        color = Color(0xFFDADADA) // Fallback color
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profilepicture),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    // Name and Email
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = userName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = userEmail,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Settings and Favorites
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .heightIn(min = 48.dp),
                    horizontalArrangement = Arrangement.spacedBy(100.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconWithText(iconId = favoriteIcon, text = "Favorites")
                    IconWithText(iconId = settingsIcon, text = "Settings")
                }

                // Separator Line
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    color = Color.Gray.copy(alpha = 0.5f),
                    thickness = 1.dp
                )

                // Posts Section
                Text(
                    text = "Posts",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                if (isLoading) {
                    // Show a loading indicator while blog posts are being fetched
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(
                        // LazyColumn configuration...
                    ) {
                        items(blogPosts) { blogPost ->

                                BlogCard(
                                    blogPost = blogPost,
                                    onDeleteClick = { blogPostId ->
                                        viewModel.deleteBlogPost(blogPostId)
                                    },
                                    onEditClick = { blogPostId ->
                                        navController.navigate("updatePostScreen/$blogPostId")
                                    }

                                )
                        }
                    }
                }
            }
        }
    }
}







@Composable
fun IconWithText(iconId: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun BlogCard(blogPost: BlogPost, onDeleteClick: (String) -> Unit, onEditClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(40.dp)
            .padding(vertical = 10.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set a white background for the card
        )
    ) {
        Column (modifier = Modifier.background(Color.White)) {
            // Blog Image
            Image(
                painter = painterResource(R.drawable.biceps),
                contentDescription = "Blog Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            // Blog Information (title and subheading from BlogInformation.kt)
            BlogInformation(
                blogTitle = blogPost.title,
                blogSubheading =blogPost.subheading,
                modifier = Modifier
            )

            // Blog Category and Icons Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Spreads category and icons apart
            ) {
                // Blog Category
                Surface(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                ) {
                    Text(
                        text = blogPost.category,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }

                // Icons (Edit and Delete)
                Row {
                    IconButton(onClick = { onEditClick(blogPost.id) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_edit),
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { onDeleteClick(blogPost.id) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_delete),
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    WellnessappTheme(darkTheme = false) {

        ProfileScreen(navController = rememberNavController())
    }
}