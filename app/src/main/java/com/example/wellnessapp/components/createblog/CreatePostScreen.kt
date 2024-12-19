package com.example.wellnessapp.components.createblog

import android.net.Uri
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
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavController, blogPostViewModel: BlogPostViewModel = hiltViewModel()) {
    var category by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var subheading by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val authorName = "Alif Hakimi"

    // Image Picker Launcher
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri = it }
        }

    // Collect the StateFlow with collectAsState
    val isPostCreated by blogPostViewModel.isPostCreated.collectAsState()

    // Navigate back after post creation
    if (isPostCreated) {
        Toast.makeText(LocalContext.current, "Post created successfully", Toast.LENGTH_SHORT).show()
        navController.navigate("home") // Navigate back to home screen
    }

    Scaffold(
        topBar = { CreateTopAppBar(onBackClick = { navController.navigate("home") }) },
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
                    .verticalScroll(rememberScrollState()) // Make the column scrollable
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Category DropDown
                CategoryDropDown(
                    selectedCategory = category,
                    onCategorySelected = { selectedCategory ->
                        category = selectedCategory
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title Field
                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
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
                    onValueChange = { subheading = it },
                    label = { Text("Subheading") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
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
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.White),
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

                // Image Selection
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
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

                // Display selected image
                imageUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Post Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            // Generate the formatted publication date
                            val publicationDate = formatTimestampToDate(System.currentTimeMillis())

                            // Create the BlogPost object and pass it to the ViewModel
                            blogPostViewModel.createPost(
                                title = title,
                                subheading = subheading,
                                description = description,
                                authorName = authorName,
                                publicationDate = publicationDate,
                                category = category,
                                imageUri = imageUri
                            )
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "Post",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateScreen() {
    val navController = rememberNavController()
    WellnessappTheme(darkTheme = false) {
        CreatePostScreen(navController = navController)
    }
}
