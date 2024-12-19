package com.example.wellnessapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellnessapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    var loginState = mutableStateOf<LoginState>(LoginState.Idle)
    var errorMessage = mutableStateOf("")

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val userEmail: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    fun login(email: String, password: String) {
        loginState.value = LoginState.Loading

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            loginState.value = if (result.isSuccess) {
                LoginState.Success(userEmail = email)
            } else {
                LoginState.Error(message = result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun signUp(email: String, password: String) {
        loginState.value = LoginState.Loading

        viewModelScope.launch {
            val result = authRepository.signUp(email, password)
            loginState.value = if (result.isSuccess) {
                LoginState.Success(userEmail = email)
            } else {
                LoginState.Error(message = result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}
