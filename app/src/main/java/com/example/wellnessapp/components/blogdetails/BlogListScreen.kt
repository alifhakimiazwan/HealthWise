package com.example.wellnessapp.components.blogdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellnessapp.components.createblog.BlogPostViewModel
import com.example.wellnessapp.components.blogdetails.BlogItem


@Composable
fun BlogListScreen(viewModel: BlogPostViewModel = hiltViewModel(), onClick: (String) -> Unit) {
    // Observe the list of blog posts
    val blogPosts = viewModel.blogPosts.collectAsState().value // Access the value directly here

    // Fetch blog posts if not already loaded
    LaunchedEffect(Unit) {
        viewModel.fetchBlogPosts()
    }

    Column {
        blogPosts.forEach { blogPost ->
            BlogItem(blogPost = blogPost, onClick = onClick)
        }
    }
}
