package com.example.anitrack.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anitrack.R
import CustomTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
@Composable
fun EditProfileDialog(
    onDismissRequest: () -> Unit,
    onDeleteAccountClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var isPictureDialogOpen by remember { mutableStateOf(false) }
    var isDeleteDialogOpen by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
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
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismissRequest() }
                    )

                    Text(
                        text = "Edit Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                        maxLines = 1
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
                    label = "UserName",
                    onValueChange = { username = it }
                )
                CustomTextField(
                    label = "Description",
                    onValueChange = { description = it }
                )
                CustomTextField(
                    label = "Email",
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showMessage = "Details saved successfully!" },
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
                    onValueChange = { password = it }
                )
                CustomTextField(
                    label = "Repeat Password",
                    isPassword = true,
                    onValueChange = { repeatPassword = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showMessage = "Password changed successfully!" },
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
                    onClick = { isPictureDialogOpen = true },
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

                // Show success message
                if (showMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = showMessage,
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

    // Dialog for choosing a profile picture
    if (isPictureDialogOpen) {
        PictureSelectionDialog(
            onDismissRequest = { isPictureDialogOpen = false },
            onSaveClick = {
                isPictureDialogOpen = false
                showMessage = "Image saved successfully!"
            }
        )
    }

    // Confirmation dialog for deleting account
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
fun PictureSelectionDialog(
    onDismissRequest: () -> Unit,
    onSaveClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select a Photo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Placeholder for photo gallery logic
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Photo Gallery Placeholder", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onSaveClick,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save")
                }
            }
        }
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
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    ) {
                        Text(text = "Cancel")
                    }

                    Button(
                        onClick = onConfirmDelete,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    ) {
                        Text(text = "Delete", color = Color.White)
                    }
                }
            }
        }
    }
}


