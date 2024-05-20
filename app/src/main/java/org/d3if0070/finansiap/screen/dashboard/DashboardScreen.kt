package org.d3if0070.finansiap.screen.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.FinansiapTheme


@Composable
fun DashboardScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(bottomBar = {
        BottomNavBar(navController = navController, Screen.Dashboard.route)
    },
        containerColor = Color.White) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Kotak pertama (untuk teks)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "List Grup",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = -130.dp, y = -70.dp)
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tagihan Mendatang",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = -70.dp, y = -280.dp)
            )
        }

        // Kotak kedua (untuk bentuk)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(x = 100, y = 900) }
                    .size(width = 319.dp, height = 320.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Transparent)
                    .border(
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFEFB643)),
                        shape = RoundedCornerShape(5.dp)
                    )
            ) {
                // Content inside the Box (if any)
            }
        }

        // Kotak ketiga (untuk bentuk lainnya)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .offset(y = -200.dp)
                    .size(width = 291.dp, height = 111.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Transparent)
                    .border(
                        border = androidx.compose.foundation.BorderStroke(3.dp, Color(0xFFEFB643)),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                // Content inside the Box (if any)
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DashboardScreenPreview() {
    FinansiapTheme {
        DashboardScreen(rememberNavController())
    }
}
