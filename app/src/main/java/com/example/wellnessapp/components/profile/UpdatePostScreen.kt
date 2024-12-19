package com.example.wellnessapp.components.profile

import com.example.wellnessapp.components.createblog.BlogPostViewModel
import com.example.wellnessapp.components.createblog.CategoryDropDown
import com.example.wellnessapp.components.createblog.CreateTopAppBar



import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.wellnessapp.R
import com.example.wellnessapp.action.formatTimestampToDate
import com.example.wellnessapp.components.home.WellnessBottomAppBar
import com.example.wellnessapp.ui.themeTheme.kt.WellnessappTheme
import com.example.wellnessapp.model.BlogPost



@Composable
fun UpdatePostScreen(
    navController: NavController,
    blogPostId: String?,
    blogPostViewModel: BlogPostViewModel = hiltViewModel()
) {
    // Fetch the existing blog post data
    val blogPostState = blogPostViewModel.selectedBlogPost.collectAsState()

    LaunchedEffect(blogPostId) {
        if (blogPostId != null) {
            blogPostViewModel.getBlogPostById(blogPostId)
        }
    }

    when (val blogPost = blogPostState.value) {
        null -> {
            // Show a loading state while the blog post is being fetched
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {
            Log.d("UpdatePostScreen", "Loaded blog post: $blogPost")
            // Initialize state with existing values
            var category by remember { mutableStateOf(blogPost.category) }
            var title by remember { mutableStateOf(blogPost.title) }
            var subheading by remember { mutableStateOf(blogPost.subheading) }
            var description by remember { mutableStateOf(blogPost.description) }
            var imageUri by remember { mutableStateOf<Uri?>(null) }
            val authorName = blogPost.authorName

            UpdatePostContent(
                navController = navController,
                category = category,
                title = title,
                subheading = subheading,
                description = description,
                imageUri = imageUri,
                authorName = authorName,
                onCategoryChange = { category = it },
                onTitleChange = { title = it },
                onSubheadingChange = { subheading = it },
                onDescriptionChange = { description = it },
                onImageUriChange = { imageUri = it },
                onUpdateClick = {
                    val publicationDate = formatTimestampToDate(System.currentTimeMillis())

                    blogPostViewModel.editBlogPost(
                        blogPostId = blogPostId ?: "",
                        title = title,
                        subheading = subheading,
                        description = description,
                        authorName = authorName,
                        publicationDate = publicationDate,
                        category = category,
                        imageUri = imageUri
                    )
                    navController.navigate("profile")
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePostContent(
    navController: NavController,
    category: String,
    title: String,
    subheading: String,
    description: String,
    imageUri: Uri?,
    authorName: String,
    onCategoryChange: (String) -> Unit,
    onTitleChange: (String) -> Unit,
    onSubheadingChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageUriChange: (Uri?) -> Unit,
    onUpdateClick: () -> Unit
) {
    val scrollState = rememberScrollState() // Remember the scroll state

    Scaffold(
        topBar = { CreateTopAppBar(onBackClick = { navController.navigate("profile") }) },
        bottomBar = {
            WellnessBottomAppBar(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )
        },
        modifier = Modifier.background(Color.White),
        containerColor = Color.White // Scaffold's background
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState) // Add scroll behavior
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Category DropDown
                CategoryDropDown(
                    selectedCategory = category,
                    onCategorySelected = { onCategoryChange(it) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title Field
                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = title,
                    onValueChange = { onTitleChange(it) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Subheading Field
                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = subheading,
                    onValueChange = { onSubheadingChange(it) },
                    label = { Text("Subheading") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description Field
                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = description,
                    onValueChange = { onDescriptionChange(it) },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    maxLines = 20,
                    minLines = 2,
                    singleLine = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Image Display and Selection
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Gray)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    val imagePickerLauncher =
                        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                            onImageUriChange(uri)
                        }

                    IconButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_image),
                            contentDescription = "Select Image",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = if (imageUri == null) "No image selected" else "Image Selected",
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onUpdateClick,
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "Update",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
