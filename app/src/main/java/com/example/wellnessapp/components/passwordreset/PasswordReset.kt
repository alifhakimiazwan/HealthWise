package com.example.wellnessapp.components.passwordreset

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wellnessapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordResetScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Your password has been reset message
        Text(
            text = "Your password has been reset.",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Logo on the right
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_logo_done),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp),
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Back to Log In text
        Text(
            text = "Back to ",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Log In link
        Text(
            text = "Log In.",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewPasswordResetScreen() {
//    WellnessappTheme {
//        PasswordResetScreen()
//    }
//
//}
