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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.anitrack.data.DatabaseCollections
import com.example.anitrack.network.AuthState
import android.net.Uri
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.example.anitrack.R
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

    val trimmedUsername = username.trim()
    val trimmedEmail = email.trim()
    val trimmedDescription = if (description.isBlank()) null else description.trim()

    val usernameValid = trimmedUsername.length >= 2 && !showMessage.startsWith(stringResource(R.string.username_taken))
    val emailFormatValid = android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()
    val emailValid = emailFormatValid && !showMessage.startsWith(stringResource(R.string.email_registered))

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
                            text = stringResource(R.string.edit_profile_title),
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

                    Text(
                        text = stringResource(R.string.profile_details),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    CustomTextField(
                        label = stringResource(R.string.username),
                        onValueChange = { username = it },
                        initialValue = username,
                        isValid = usernameValid
                    )
                    CustomTextField(
                        label = stringResource(R.string.description),
                        onValueChange = { description = it },
                        initialValue = description,
                        isValid = true
                    )
                    CustomTextField(
                        label = stringResource(R.string.email),
                        onValueChange = { email = it },
                        initialValue = email,
                        isValid = emailValid
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.change_password),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    CustomTextField(
                        label = stringResource(R.string.password),
                        isPassword = true,
                        onValueChange = { password = it },
                        initialValue = "",
                        isValid = passwordEmpty || passwordValid
                    )
                    CustomTextField(
                        label = stringResource(R.string.repeat_password),
                        isPassword = true,
                        onValueChange = { repeatPassword = it },
                        initialValue = "",
                        isValid = passwordEmpty || passwordValid
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.profile_picture),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    selectedImageUri?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(model = uri),
                            contentDescription = stringResource(R.string.profile_picture),
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
                        Text(text = stringResource(R.string.pick_new_picture))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                showMessage = ""

                                if (trimmedUsername.length < 2) {
                                    showMessage = "Username must be at least 2 characters"
                                    return@launch
                                }

                                if (!emailFormatValid) {
                                    showMessage = "Invalid email format"
                                    return@launch
                                }

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

                                if (trimmedUsername != currentUsername) {
                                    val usernameTaken = viewModel.isFieldTaken(DatabaseCollections.Users, "username", trimmedUsername)
                                    if (usernameTaken) {
                                        showMessage = "Username already taken"
                                        return@launch
                                    }
                                }

                                if (trimmedEmail != currentEmail) {
                                    val emailTaken = viewModel.isFieldTaken(DatabaseCollections.Users, "email", trimmedEmail)
                                    if (emailTaken) {
                                        showMessage = "Email already registered"
                                        return@launch
                                    }
                                }

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
                        Text(text = stringResource(R.string.apply_all_changes))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { isDeleteDialogOpen = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.delete_account), color = Color.White)
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
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.confirm_deletion_title)) },
        text = { Text(text = stringResource(R.string.confirm_deletion_message)) },
        confirmButton = {
            Button(
                onClick = onConfirmDelete,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = stringResource(R.string.delete), color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}