package com.example.anitrack.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.anitrack.R
import com.example.anitrack.network.AuthState

@Composable
fun EditProfileDialog(
    onDismissRequest: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    userId: String,
    currentUsername: String,
    currentEmail: String,
    currentDescription: String?,
    currentProfilePictureUrl: String,
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

    LaunchedEffect(editState) {
        when (editState) {
            is AuthState.Success -> {
                showMessage = "Operation completed successfully!"
                // Close the dialog and reload the user data to reflect changes
                onDismissRequest()
                viewModel.loadUserProfileAndFavorites(userId)
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

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_24),
                        contentDescription = "Back",
                        colorFilter = ColorFilter.tint(Color.Black),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismissRequest() }
                    )

                    Text(
                        text = "Edit Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        maxLines = 1
                    )
                }

                // Current Profile Picture
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = if (currentProfilePictureUrl.isNotEmpty()) {
                            rememberAsyncImagePainter(model = currentProfilePictureUrl)
                        } else {
                            painterResource(id = R.drawable.default_pfp)
                        },
                        contentDescription = "Current Profile Picture",
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Change Details Section
                Text(
                    text = "Change Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CustomTextField(
                    label = "Username",
                    onValueChange = { username = it },
                    initialValue = username
                )
                CustomTextField(
                    label = "Description",
                    onValueChange = { description = it },
                    initialValue = description
                )
                CustomTextField(
                    label = "Email",
                    onValueChange = { email = it },
                    initialValue = email
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.updateUserDetails(
                            userId = userId,
                            currentEmail = currentEmail,
                            currentUsername = currentUsername,
                            newUsername = username,
                            newEmail = email,
                            newDescription = if (description.isBlank()) null else description
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save Details")
                }

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
                    initialValue = ""
                )
                CustomTextField(
                    label = "Repeat Password",
                    isPassword = true,
                    onValueChange = { repeatPassword = it },
                    initialValue = ""
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.updateUserPassword(password, repeatPassword) },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save Password")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Change Profile Picture Section
                Text(
                    text = "Change Profile Picture",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { onProfilePictureChangeRequest() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Change Picture")
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

                // Show message
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
