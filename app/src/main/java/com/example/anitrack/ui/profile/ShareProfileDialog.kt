package com.example.anitrack.ui.profile

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.common.BitMatrix
import android.graphics.Bitmap

@Composable
fun ShareProfileDialog(
    userId: String,
    onDismiss: () -> Unit
) {
    val deepLinkQRCode = remember { generateQRCodeForDeepLink(userId) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Share Profile") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Scan the QR code to visit this profile:")
                Spacer(modifier = Modifier.height(16.dp))
                Image(bitmap = deepLinkQRCode.asImageBitmap(), contentDescription = "QR Code")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

fun generateQRCode(content: String, size: Int = 512): Bitmap {
    val hints = hashMapOf<EncodeHintType, Any>()
    hints[EncodeHintType.MARGIN] = 1
    val qrCodeWriter = QRCodeWriter()
    val bitMatrix: BitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bitmap
}

fun generateQRCodeForDeepLink(userId: String, size: Int = 512): Bitmap {
    val deepLinkUrl = "anitrack://perfil/user/$userId"
    return generateQRCode(deepLinkUrl, size)
}
