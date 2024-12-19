package com.example.wellnessapp.components.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wellnessapp.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val email = remember { mutableStateOf("") }
//    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val signUpState by remember { authViewModel.loginState }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Title
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 60.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email Field
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 24.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFDCE7C8),
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

//        // Username Field
//        OutlinedTextField(
//            value = username.value,
//            onValueChange = { username.value = it },
//            label = { Text("Username") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.White)
//                .padding(bottom = 24.dp),
//            singleLine = true,
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                containerColor = Color(0xFFDCE7C8),
//                focusedTextColor = MaterialTheme.colorScheme.onSurface,
//                focusedBorderColor = MaterialTheme.colorScheme.primary,
//                unfocusedBorderColor = MaterialTheme.colorScheme.outline
//            )
//        )

        // Password Field
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 24.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFDCE7C8),
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 24.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFDCE7C8),
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        // Validate form
        val isFormValid = email.value.isNotBlank() &&
                password.value.isNotBlank() &&
                password.value == confirmPassword.value

        // Sign Up Button
        Button(
            onClick = {
                authViewModel.signUp(email.value, password.value)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid && signUpState !is AuthViewModel.LoginState.Loading,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            Text("Sign Up", fontSize = 18.sp)
        }

        // Handle sign-up state
        when (signUpState) {
            is AuthViewModel.LoginState.Loading -> {
                Text("Signing up...", color = MaterialTheme.colorScheme.primary)
            }
            is AuthViewModel.LoginState.Error -> {
                Text(
                    text = (signUpState as AuthViewModel.LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is AuthViewModel.LoginState.Success -> {
                navController.navigate("home") {
                    popUpTo("signup") { inclusive = true }
                }
            }
            else -> {}
        }
    }
}
