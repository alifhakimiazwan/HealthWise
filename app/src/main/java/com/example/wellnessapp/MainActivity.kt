package com.example.wellnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wellnessapp.components.blogdetails.BlogDetailsPage
import com.example.wellnessapp.components.createblog.CreatePostScreen
import com.example.wellnessapp.components.forgotpassword.ForgotPasswordScreen
import com.example.wellnessapp.components.home.HomePage
import com.example.wellnessapp.components.login.LoginScreen
import com.example.wellnessapp.components.passwordreset.PasswordResetScreen
import com.example.wellnessapp.components.profile.ProfileScreen
import com.example.wellnessapp.components.profile.UpdatePostScreen
import com.example.wellnessapp.components.signup.SignUpScreen
import com.example.wellnessapp.data.blogs
import com.example.wellnessapp.ui.themeTheme.kt.WellnessappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use WindowCompat to enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WellnessappTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize().background(Color.White)

                ) {
                    MainApp()
                }
            }
        }
    }
}
@Composable
fun MainApp() {
    val navController = rememberNavController()

    val bottomBarRoutes = listOf("home", "createPost", "profile")

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Navigation Host
        NavHost(
            navController = navController,
            startDestination = "login", // Set the login screen as the default
            modifier = Modifier.fillMaxSize()
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("signup") {
                SignUpScreen(navController)
            }
            composable("forgotPassword") {
                ForgotPasswordScreen(navController)
            }
            composable("passwordReset") {
                PasswordResetScreen(navController)
            }
            composable("home") {
                HomePage(navController)
            }
            composable("createPost") {
                CreatePostScreen(navController)
            }
            composable("profile") {
                ProfileScreen(navController)
            }
            composable(
                route = "blog_details/{blogId}",
                arguments = listOf(navArgument("blogId") { type = NavType.StringType })
            ) { backStackEntry ->
                val blogId = backStackEntry.arguments?.getString("blogId")
                val blogPost = blogs.find { it.id == blogId }
                if (blogPost != null) {
                    BlogDetailsPage(blogPost, navController)
                }
            }
            composable("updatePostScreen/{blogPostId}") { backStackEntry ->
                val blogPostId = backStackEntry.arguments?.getString("blogPostId")!!
                UpdatePostScreen(navController = navController, blogPostId = blogPostId)
            }

        }
    }
}





@Preview(showBackground = true)
@Composable
fun WellnessPreview() {
    WellnessappTheme(darkTheme = false) {
        MainApp()
    }
}
