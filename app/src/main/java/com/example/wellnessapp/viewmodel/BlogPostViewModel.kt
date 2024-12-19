package com.example.wellnessapp.components.createblog

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellnessapp.model.BlogPost
import com.example.wellnessapp.repository.BlogPostRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlogPostViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val blogPostRepository: BlogPostRepository
) : ViewModel() {

    private val _isPostCreated = MutableStateFlow(false)
    val isPostCreated: StateFlow<Boolean> = _isPostCreated

    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts: StateFlow<List<BlogPost>> = _blogPosts

    private val auth = FirebaseAuth.getInstance()

    // Function to create a new blog post
    fun createPost(
        title: String,
        subheading: String,
        description: String,
        authorName: String,
        publicationDate: String,
        category: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                // Upload the image and get the unique URL
                val imageUrl = uploadImage(imageUri)

                // Get the author's email (authorId) from Firebase Authentication
                val authorId = auth.currentUser?.email ?: "unknown@example.com"

                // Create a new BlogPost object
                val newBlogPost = BlogPost(
                    id = "",
                    title = title,
                    subheading = subheading,
                    description = description,
                    authorName = authorName,
                    publicationDate = publicationDate,
                    category = category,
                    imageUrl = imageUrl,
                    authorId = authorId
                )

                // Add the blog post to Firestore
                val documentRef = firestore.collection("blogPosts").add(newBlogPost).await()

                // Update the post's ID
                firestore.collection("blogPosts")
                    .document(documentRef.id)
                    .update("id", documentRef.id)

                _isPostCreated.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _isPostCreated.value = false
            }
        }
    }


    // Function to upload the image to Firebase Storage and return the download URL
    private suspend fun uploadImage(imageUri: Uri?): String? {
        return if (imageUri != null) {
            try {
                val storageRef = Firebase.storage.reference// Get a reference to Firebase Storage
                val imageRef = storageRef.child("blogImages/${System.currentTimeMillis()}.jpg") // Unique path for each image
                val uploadTask = imageRef.putFile(imageUri).await() // Upload the image
                imageRef.downloadUrl.await().toString() // Get and return the download URL
            } catch (e: Exception) {
                e.printStackTrace()
                null // Return null if there's an error
            }
        } else {
            null // Return null if there's no image
        }
    }


    // Function to fetch all blog posts
    fun fetchBlogPosts() {
        viewModelScope.launch {
            try {
                val posts = firestore.collection("blogPosts")
                    .get()
                    .await()
                    .documents
                    .map { document ->
                        BlogPost(
                            id = document.id,
                            title = document.getString("title") ?: "",
                            subheading = document.getString("subheading") ?: "",
                            authorName = document.getString("authorName") ?: "",
                            description = document.getString("description") ?: "",
                            publicationDate = document.getString("publicationDate") ?: "",
                            category = document.getString("category") ?: "",
                            imageUrl = document.getString("imageUrl") ?: "",
                            authorId = document.getString("authorId") ?: "" // Add authorId field
                        )
                    }
                _blogPosts.value = posts
            } catch (e: Exception) {
                Log.e("BlogPostViewModel", "Error fetching blog posts", e)
            }
        }
    }

    // Function to fetch blog posts for the current user based on their email (authorId)
    fun fetchUserBlogPosts(userEmail: String) {
        viewModelScope.launch {
            try {
                // Query Firestore for blog posts with the given authorId
                val posts = firestore.collection("blogPosts")
                    .whereEqualTo("authorId", userEmail) // Filter by the user's email
                    .get()
                    .await()
                    .documents
                    .map { document ->
                        BlogPost(
                            id = document.id,
                            title = document.getString("title") ?: "",
                            subheading = document.getString("subheading") ?: "",
                            authorName = document.getString("authorName") ?: "",
                            description = document.getString("description") ?: "",
                            publicationDate = document.getString("publicationDate") ?: "",
                            category = document.getString("category") ?: "",
                            imageUrl = document.getString("imageUrl") ?: "",
                            authorId = document.getString("authorId") ?: "" // Add authorId field
                        )
                    }

                _blogPosts.value = posts // Update the LiveData with the filtered posts
                Log.d("BlogPostViewModel", "Fetched ${posts.size} blog posts for user: $userEmail")
            } catch (e: Exception) {
                Log.e("BlogPostViewModel", "Error fetching user's blog posts", e)
            }
        }
    }



    // Function to delete a blog post by its ID
    fun deleteBlogPost(blogPostId: String) {
        viewModelScope.launch {
            try {
                val isDeleted = blogPostRepository.deleteBlogPost(blogPostId)
                if (isDeleted) {
                    // Remove the deleted post from the state flow
                    _blogPosts.value = _blogPosts.value.filter { it.id != blogPostId }
                }
            } catch (e: Exception) {
                Log.e("BlogPostViewModel", "Error deleting blog post", e)
            }
        }
    }

    fun editBlogPost(
        blogPostId: String,
        title: String? = null,
        subheading: String? = null,
        description: String? = null,
        authorName: String? = null,
        publicationDate: String? = null,
        category: String? = null,
        imageUri: Uri? = null
    ) {
        viewModelScope.launch {
            try {
                // Fetch the current blog post from Firestore
                val documentRef = firestore.collection("blogPosts").document(blogPostId)
                val snapshot = documentRef.get().await()
                if (snapshot.exists()) {
                    // Prepare the updated fields
                    val updates = mutableMapOf<String, Any>()
                    title?.let { updates["title"] = it }
                    subheading?.let { updates["subheading"] = it }
                    description?.let { updates["description"] = it }
                    authorName?.let { updates["authorName"] = it }
                    publicationDate?.let { updates["publicationDate"] = it }
                    category?.let { updates["category"] = it }

                    // Upload the new image if provided
                    val imageUrl = imageUri?.let { uploadImage(it) }
                    imageUrl?.let { updates["imageUrl"] = it }

                    // Update the blog post in Firestore
                    documentRef.update(updates).await()

                    // Update the local state
                    val updatedPosts = _blogPosts.value.map { post ->
                        if (post.id == blogPostId) {
                            post.copy(
                                title = title ?: post.title,
                                subheading = subheading ?: post.subheading,
                                description = description ?: post.description,
                                authorName = authorName ?: post.authorName,
                                publicationDate = publicationDate ?: post.publicationDate,
                                category = category ?: post.category,
                                imageUrl = imageUrl ?: post.imageUrl
                            )
                        } else post
                    }
                    _blogPosts.value = updatedPosts

                    Log.d("BlogPostViewModel", "Blog post updated successfully: $blogPostId")
                } else {
                    Log.e("BlogPostViewModel", "Blog post not found: $blogPostId")
                }
            } catch (e: Exception) {
                Log.e("BlogPostViewModel", "Error editing blog post", e)
            }
        }
    }


    private val _selectedBlogPost = MutableStateFlow<BlogPost?>(null)
    val selectedBlogPost: StateFlow<BlogPost?> = _selectedBlogPost

    fun getBlogPostById(blogPostId: String) {
        viewModelScope.launch {
            try {
                val documentSnapshot = firestore.collection("blogPosts").document(blogPostId).get().await()
                if (documentSnapshot.exists()) {
                    val blogPost = documentSnapshot.toObject(BlogPost::class.java)
                    _selectedBlogPost.value = blogPost
                } else {
                    Log.e("BlogPostViewModel", "Blog post not found for ID: $blogPostId")
                    _selectedBlogPost.value = null
                }
            } catch (e: Exception) {
                Log.e("BlogPostViewModel", "Error fetching blog post by ID", e)
                _selectedBlogPost.value = null
            }
        }
    }

//
//    fun editBlogPost(
//        blogPostId: String,
//        title: String,
//        subheading: String,
//        description: String,
//        authorName: String,
//        publicationDate: String,
//        category: String,
//        imageUri: Uri?
//    ) {
//        viewModelScope.launch {
//            try {
//                blogPostRepository.updateBlogPost(
//                    id = blogPostId,
//                    title = title,
//                    subheading = subheading,
//                    description = description,
//                    authorName = authorName,
//                    publicationDate = publicationDate,
//                    category = category,
//                    imageUri = imageUri
//                )
//            } catch (e: Exception) {
//                // Handle errors (e.g., log them or notify the UI)
//            }
//        }
//    }





}