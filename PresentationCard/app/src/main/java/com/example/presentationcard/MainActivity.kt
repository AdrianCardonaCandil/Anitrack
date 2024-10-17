package com.example.presentationcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationcard.ui.theme.PresentationCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PresentationCardTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFD1E7D3)) // Darker green background
                    ) {
                        PresentationUpperCard(
                            name = "Jennifer Doe",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                        ContactInfo(
                            phone = "+11 (123) 444 555 666",
                            social = "@AndroidDev",
                            email = "jen.doe@android.com",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PresentationUpperCard(name: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        val image = painterResource(R.drawable.android_logo)
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFF073042)) // Darker green
        ) {
            Image(
                painter = image,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = name,
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.subtitle),
            color = Color(0xFF0A542F), // Darker green
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun ContactInfo(phone: String, social: String, email: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_phone),
                contentDescription = stringResource(R.string.phone_icon_desc),
                tint = Color(0xFF004d00) // Darker green
            )
            Text(
                text = phone,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_social),
                contentDescription = stringResource(R.string.social_icon_desc),
                tint = Color(0xFF004d00) // Darker green
            )
            Text(
                text = social,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_email),
                contentDescription = stringResource(R.string.email_icon_desc),
                tint = Color(0xFF004d00) // Darker green
            )
            Text(
                text = email,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PresentationPreview() {
    PresentationCardTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD1E7D3)) // Darker green background
            ) {
                PresentationUpperCard(
                    name = "Jennifer Doe",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
                ContactInfo(
                    phone = "+11 (123) 444 555 666",
                    social = "@AndroidDev",
                    email = "jen.doe@android.com",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(30.dp)
                )
            }
        }
    }
}