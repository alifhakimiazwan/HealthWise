package com.example.wellnessapp.components.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wellnessapp.ui.themeTheme.kt.WellnessappTheme
import com.example.wellnessapp.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    // Declare mutable state variables for username and password
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val loginState by remember { authViewModel.loginState }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App name
        Text(
            text = "HealthWise",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 60.sp,
            modifier = Modifier.padding(bottom = 32.dp) // Space below app name
        )

        // Username input
        OutlinedTextField(
            value = email.value, // Use .value to access the state
            onValueChange = { email.value = it }, // Set value with .value
            textStyle = MaterialTheme.typography.bodySmall,
            label = { Text("Email") },
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 24.dp)
        )

        // Password input
        OutlinedTextField(
            value = password.value, // Use .value to access the state
            onValueChange = { password.value = it }, // Set value with .value
            label = { Text("Password") },
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 24.dp),
            visualTransformation = PasswordVisualTransformation(), // Hides password characters
        )

        // Handle login button state
        Button(
            onClick = {
                authViewModel.login(email.value, password.value)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = email.value.isNotBlank() && password.value.isNotBlank() && loginState !is AuthViewModel.LoginState.Loading
        ) {
            Text("Log In")
        }

        // Handle login state
        when (loginState) {
            is AuthViewModel.LoginState.Loading -> {
                // Show a loading indicator or message
                Text("Logging in...", color = MaterialTheme.colorScheme.primary)
            }
            is AuthViewModel.LoginState.Error -> {
                // Show an error message
                Text(
                    text = (loginState as AuthViewModel.LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is AuthViewModel.LoginState.Success -> {
                // Navigate to home screen on success
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            else -> {}
        }

        // Sign up navigation
        Text(
            text = "Don't have an account? ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Sign up here",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate("signup")
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    WellnessappTheme {
        LoginScreen(navController = rememberNavController())
    }
}
