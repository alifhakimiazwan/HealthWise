package com.example.wellnessapp.components.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavController) {

    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Instruction Text
        Text(
            text = "Enter the email you have registered with and we will send you a link to reset your password.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
        )

        // Email Field
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFDCE7C8), // Background inside the box
                focusedTextColor = MaterialTheme.colorScheme.onSurface,   // Text color
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Focus border
                unfocusedBorderColor = MaterialTheme.colorScheme.outline // Unfocused border
            )
        )

        // Send Email Button
        Button(
            onClick = {
                // Handle sending email click
                println("Email sent to: ${email.value}")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = email.value.isNotBlank()
        ) {
            Text("Send Email",
                fontSize = 18.sp
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewForgotPasswordScreen() {
//    WellnessappTheme {
//        ForgotPasswordScreen()
//    }
//
//}
