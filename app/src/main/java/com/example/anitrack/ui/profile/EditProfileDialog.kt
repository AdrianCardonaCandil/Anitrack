package com.example.anitrack.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.anitrack.data.DatabaseCollections
import com.example.anitrack.network.AuthState
import android.net.Uri
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun EditProfileDialog(
    onDismissRequest: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    userId: String,
    currentUsername: String,
    currentEmail: String,
    currentDescription: String?,
    selectedImageUri: Uri?,
    viewModel: ProfileViewModel,
    onProfilePictureChangeRequest: () -> Unit
) {
    var username by remember { mutableStateOf(currentUsername) }
    var description by remember { mutableStateOf(currentDescription ?: "") }
    var email by remember { mutableStateOf(currentEmail) }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var showMessage by remember { mutableStateOf("") }

    val editState by viewModel.profileEditState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(editState) {
        when (editState) {
            is AuthState.Success -> {
                showMessage = "Operation completed successfully!"
                onDismissRequest()
                viewModel.loadUserProfileAndFavorites(userId)
                viewModel.resetProfileEditState()
            }
            is AuthState.Error -> {
                showMessage = (editState as AuthState.Error).exception.message ?: "Unknown error"
            }
            is AuthState.ValidationError -> {
                showMessage = (editState as AuthState.ValidationError).message
            }
            is AuthState.Loading -> {
                showMessage = "Loading..."
            }
            else -> Unit
        }
    }

    var isDeleteDialogOpen by remember { mutableStateOf(false) }

    // Compute validity states for fields to show red/green indicators
    val trimmedUsername = username.trim()
    val trimmedEmail = email.trim()
    val trimmedDescription = if (description.isBlank()) null else description.trim()

    val usernameValid = trimmedUsername.length >= 2 && !showMessage.startsWith("Username already taken")
    val emailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()
    val emailValid = emailFormatValid && !showMessage.startsWith("Email already registered")

    val passwordEmpty = password.isEmpty() && repeatPassword.isEmpty()
    val passwordValid = if (!passwordEmpty) {
        password.length >= 8 && password.any { it.isDigit() } && password.any { it.isUpperCase() } && (password == repeatPassword)
    } else true

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 600.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Edit Profile",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f),
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { onDismissRequest() },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Close")
                        }
                    }

                    // Profile Details
                    Text(
                        text = "Profile Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    CustomTextField(
                        label = "Username",
                        onValueChange = { username = it },
                        initialValue = username,
                        isValid = usernameValid
                    )
                    CustomTextField(
                        label = "Description",
                        onValueChange = { description = it },
                        initialValue = description,
                        isValid = true // Description optional, no strict validation
                    )
                    CustomTextField(
                        label = "Email",
                        onValueChange = { email = it },
                        initialValue = email,
                        isValid = emailValid
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Change Password Section
                    Text(
                        text = "Change Password",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    CustomTextField(
                        label = "Password",
                        isPassword = true,
                        onValueChange = { password = it },
                        initialValue = "",
                        isValid = passwordEmpty || passwordValid
                    )
                    CustomTextField(
                        label = "Repeat Password",
                        isPassword = true,
                        onValueChange = { repeatPassword = it },
                        initialValue = "",
                        isValid = passwordEmpty || passwordValid
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Profile Picture Section
                    Text(
                        text = "Profile Picture",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    selectedImageUri?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(model = uri),
                            contentDescription = "Selected profile picture",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp)
                        )
                    }

                    Button(
                        onClick = { onProfilePictureChangeRequest() },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Pick New Picture")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Apply All Changes Button
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                showMessage = ""

                                // Validate username
                                if (trimmedUsername.length < 2) {
                                    showMessage = "Username must be at least 2 characters"
                                    return@launch
                                }

                                // Validate email format
                                if (!emailFormatValid) {
                                    showMessage = "Invalid email format"
                                    return@launch
                                }

                                // Validate password if provided
                                if (!passwordEmpty) {
                                    if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
                                        showMessage = "Password must be at least 8 characters, include a number, and an uppercase letter"
                                        return@launch
                                    }
                                    if (password != repeatPassword) {
                                        showMessage = "Passwords do not match"
                                        return@launch
                                    }
                                }

                                // Check uniqueness if username changed
                                if (trimmedUsername != currentUsername) {
                                    val usernameTaken = viewModel.isFieldTaken(DatabaseCollections.Users, "username", trimmedUsername)
                                    if (usernameTaken) {
                                        showMessage = "Username already taken"
                                        return@launch
                                    }
                                }

                                // Check uniqueness if email changed
                                if (trimmedEmail != currentEmail) {
                                    val emailTaken = viewModel.isFieldTaken(DatabaseCollections.Users, "email", trimmedEmail)
                                    if (emailTaken) {
                                        showMessage = "Email already registered"
                                        return@launch
                                    }
                                }

                                // If we reach here, validations passed
                                viewModel.updateUserDetails(
                                    userId = userId,
                                    currentEmail = currentEmail,
                                    currentUsername = currentUsername,
                                    newUsername = trimmedUsername,
                                    newEmail = trimmedEmail,
                                    newDescription = trimmedDescription
                                )

                                if (!passwordEmpty) {
                                    viewModel.updateUserPassword(password, repeatPassword)
                                }

                                if (selectedImageUri != null) {
                                    viewModel.updateUserProfilePictureFromUri(userId, selectedImageUri)
                                }
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Apply All Changes")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Delete Account Section
                    Button(
                        onClick = { isDeleteDialogOpen = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Delete Account", color = Color.White)
                    }

                    if (showMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = showMessage,
                            color = if (editState is AuthState.Success) Color.Green else Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }

    if (isDeleteDialogOpen) {
        DeleteAccountConfirmationDialog(
            onDismissRequest = { isDeleteDialogOpen = false },
            onConfirmDelete = {
                isDeleteDialogOpen = false
                onDeleteAccountClick()
            }
        )
    }
}

@Composable
fun DeleteAccountConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Confirm Deletion",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Are you sure you want to delete your account? This action cannot be undone.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onDismissRequest,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(text = "Cancel")
                    }

                    Button(
                        onClick = onConfirmDelete,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(text = "Delete", color = Color.White)
                    }
                }
            }
        }
    }
}
