package com.example.wellnessapp.data

import androidx.annotation.DrawableRes
import com.example.wellnessapp.R

data class BlogPost(
    val id: String,                  // Unique identifier for the blog post
    val title: String,               // Title of the blog post
    val subheading: String,          // Short description or subheading
    val authorName: String,          // Author's name
    val publicationDate: String,     // Publication date
    val category: String,            // Category of the blog post
    val description: String           // Full description of the blog post
)

val blogs = listOf(
    BlogPost(
        id = "1",
        title = "Bicep Blast",
        subheading = "Effective exercises to build stronger biceps",
        authorName = "John Doe",
        publicationDate = "2024-10-01",
        category = "Fitness",
        description = "In this blog, we delve into the anatomy of the bicep muscle and discuss various exercises specifically designed to target and strengthen it. From dumbbell curls to chin-ups, we cover techniques that will not only enhance muscle growth but also improve overall arm strength. You'll find tips on proper form, rep ranges, and how to incorporate these exercises into your weekly workout routine for maximum effectiveness."
    ),
    BlogPost(
        id = "2",
        title = "Core Strength",
        subheading = "Essential core workouts for stability and balance",
        authorName = "Jane Smith",
        publicationDate = "2024-10-05",
        category = "Diets",
        description = "A strong core is fundamental for overall fitness and athletic performance. This blog explores a range of core-strengthening exercises, including planks, Russian twists, and leg raises, designed for both beginners and advanced practitioners. We also discuss the importance of core stability in daily activities and sports, and how a strong core can help prevent injuries. Each exercise is explained in detail, with variations to suit different fitness levels."
    ),
    BlogPost(
        id = "3",
        title = "Leg Day Essentials",
        subheading = "Top routines to develop powerful legs",
        authorName = "Alice Johnson",
        publicationDate = "2024-10-10",
        category = "Lifestyle",
        description = "Leg day can be daunting, but it's crucial for balanced muscle development and overall strength. In this post, we outline the best leg day routines, including squats, lunges, and deadlifts, along with tips on how to effectively structure your leg workouts. We'll also discuss common mistakes to avoid and how to push through the pain to achieve your fitness goals. Whether you're aiming for muscle growth or endurance, these routines will help you build powerful legs."
    ),
    BlogPost(
        id = "4",
        title = "Cardio for Endurance",
        subheading = "Boost endurance with these cardio tips",
        authorName = "Bob Brown",
        publicationDate = "2024-10-15",
        category = "Cardio",
        description = "Endurance training is essential for athletes and fitness enthusiasts alike. This blog covers various cardio exercises that can boost your stamina, including running, cycling, and interval training. We'll discuss the science behind endurance training, how to gradually increase your workout intensity, and tips for maintaining motivation. With structured workouts and expert advice, you'll learn how to incorporate effective cardio into your routine to improve your overall fitness levels."
    ),
    BlogPost(
        id = "5",
        title = "Yoga for Flexibility",
        subheading = "Improve flexibility with beginner yoga poses",
        authorName = "Chris Green",
        publicationDate = "2024-10-20",
        category = "Wellness",
        description = "Yoga is not just about relaxation; it’s also an excellent practice for improving flexibility and overall body awareness. In this blog, we introduce a series of beginner-friendly yoga poses that are perfect for enhancing flexibility. Each pose is described with step-by-step instructions, along with tips for proper alignment and breathing techniques. We also discuss the mental and physical benefits of regular yoga practice, helping you understand how it can enhance your overall well-being and performance in other physical activities."
    )
)