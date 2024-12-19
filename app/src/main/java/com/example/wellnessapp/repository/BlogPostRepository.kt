package com.example.wellnessapp.repository

import android.net.Uri
import com.example.wellnessapp.model.BlogPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BlogPostRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) {

    // Function to upload image to Firebase Storage
    private suspend fun uploadImageToFirebaseStorage(imageUri: Uri?): String? {
        if (imageUri == null) return null

        val storageRef = storage.reference.child("blog_images/${System.currentTimeMillis()}")
        val uploadTask = storageRef.putFile(imageUri).await()

        // Get the download URL of the uploaded image
        return storageRef.downloadUrl.await().toString()
    }

    suspend fun addBlogPost(blogPost: BlogPost): Boolean {
        return try {
            val userEmail = auth.currentUser?.email ?: "unknown@example.com"
            // Ensure the blog post has a valid user ID
            val blogPostWithUser = blogPost.copy(authorId = userEmail, authorName = auth.currentUser?.displayName ?: "Unknown")

            // Save the blog post to Firestore
            firestore.collection("blogPosts")
                .add(blogPostWithUser)
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    // Function to create a new blog post, upload image, and save to Firestore
    suspend fun createPost(
        title: String,
        subheading: String,
        description: String,
        authorName: String,
        publicationDate: String,
        category: String,
        imageUri: Uri?
    ): Boolean {
        // Upload image and get the image URL
        val imageUrl = uploadImageToFirebaseStorage(imageUri)

        // Get the author's email (authorId) from Firebase Authentication
        val authorId = auth.currentUser?.email ?: "unknown@example.com"  // Default to a dummy email if not found

        // Create a new BlogPost object with the authorId (email)
        val blogPost = BlogPost(
            id = "", // Let Firestore auto-generate the ID
            title = title,
            subheading = subheading,
            description = description,
            authorName = authorName,
            publicationDate = publicationDate,
            category = category,
            imageUrl = imageUrl,
            authorId = authorId // Add the authorId here
        )

        // Add the blog post to Firestore
        return addBlogPost(blogPost)
    }

    // Function to fetch all blog posts from Firestore
    suspend fun fetchBlogPosts(): List<BlogPost> {
        return try {
            val snapshot = firestore.collection("blogPosts")
                .get()
                .await()

            // Map the Firestore documents to BlogPost objects
            snapshot.documents.mapNotNull { document ->
                document.toObject(BlogPost::class.java)?.copy(id = document.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getBlogPosts(): List<BlogPost> {
        val blogPosts = mutableListOf<BlogPost>()

        // Query Firestore collection
        val querySnapshot = firestore.collection("blogPosts").get().await()

        // Convert each document to a BlogPost
        for (document in querySnapshot.documents) {
            val blogPost = document.toObject(BlogPost::class.java)
            blogPost?.let { blogPosts.add(it) }
        }
        return blogPosts
    }

    // Function to fetch blog posts by a specific user
    suspend fun fetchUserBlogPosts(userEmail: String): List<BlogPost> {
        return try {
            val snapshot = firestore.collection("blogPosts").get().await()

            // Map the Firestore documents to BlogPost objects
            snapshot.documents.mapNotNull { document ->
                document.toObject(BlogPost::class.java)?.copy(id = document.id)
            }
        } catch (e: Exception) {
            emptyList() // Return empty list on failure
        }
    }

//     Function to delete a blog post by its ID
    suspend fun deleteBlogPost(blogPostId: String): Boolean {
        return try {
            // Remove the blog post from Firestore
            firestore.collection("blogPosts")
                .document(blogPostId)
                .delete()
                .await()

            // Optionally, you could delete the associated image from Firebase Storage here
            deleteImageFromStorage(blogPostId)

            true
        } catch (e: Exception) {
            false // Return false if there was an error deleting the blog post
        }
    }

//     Function to delete the image associated with the blog post from Firebase Storage
    private suspend fun deleteImageFromStorage(blogPostId: String) {
        try {
            // Assuming the image URL is stored in the blog post, you can extract and delete the image
            val imageRef = storage.reference.child("blog_images/$blogPostId")
            imageRef.delete().await()
        } catch (e: Exception) {
            // Handle any errors that occur while deleting the image
        }
    }

    // Function to update a blog post in Firestore
    suspend fun editBlogPost(
        blogPostId: String,
        updates: Map<String, Any>
    ): Boolean {
        return try {
            firestore.collection("blogPosts")
                .document(blogPostId)
                .update(updates)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
    suspend fun getBlogPostById(postId: String): BlogPost? {
        return try {
            val documentSnapshot = firestore.collection("blogPosts")
                .document(postId)
                .get()
                .await()

            // Map the Firestore document to a BlogPost object if it exists
            documentSnapshot.toObject(BlogPost::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            null // Return null if there's an error
        }
    }

    suspend fun updateBlogPost(
        id: String,
        title: String,
        subheading: String,
        description: String,
        authorName: String,
        publicationDate: String,
        category: String,
        imageUri: Uri?
    ) {
        val blogPostRef = firestore.collection("blogPosts").document(id)

        // Prepare updated data
        val updatedData = mutableMapOf<String, Any>(
            "title" to title,
            "subheading" to subheading,
            "description" to description,
            "authorName" to authorName,
            "publicationDate" to publicationDate,
            "category" to category
        )

        // Handle image upload if a new image is provided
        if (imageUri != null) {
            val imageRef = storage.reference.child("blogImages/$id")
            val uploadTask = imageRef.putFile(imageUri).await()
            val imageUrl = imageRef.downloadUrl.await().toString()
            updatedData["imageUrl"] = imageUrl
        }

        // Update Firestore document
        blogPostRef.update(updatedData).await()
    }

}