package org.d3if0070.finansiap.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if0070.finansiap.ui.theme.FinansiapTheme

@Composable
fun AlertDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { /*TODO*/ },
        modifier = Modifier.height(200.dp),

        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.Info, contentDescription = "Privacy")
                Text(
                    text = "Lengkapi Data Telebih Dahulu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        },

        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF58C73A),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "OKE")
                }
            }
        }
    )
}

@Preview
@Composable
fun AletPre() {
    FinansiapTheme {
        AlertDialog {
        }
    }
}
