package com.example.wellnessapp.model

data class BlogPost(
    val id: String,
    val title: String,
    val subheading: String,
    val description: String,
    val authorId: String,  // Added userId (or authorId)
    val authorName: String,
    val imageUrl: String?,
    val publicationDate: String,
    val category: String,
){
    constructor() : this("", "", "", "", "", "", "", "", "")
}

