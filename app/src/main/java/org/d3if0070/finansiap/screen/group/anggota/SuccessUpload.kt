package org.d3if0070.finansiap.screen.group.anggota

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessUploadScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.Group.route)
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(text = (stringResource(id = R.string.data_unggahan)))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = BackgroundBar,
                    titleContentColor = Color.White,
                ),
            )
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@SuppressLint("ResourceType")
@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Data Unggahan",
            color = BackgroundBar,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(width = 2.dp, color = Outline),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(32.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.photo_logo),
                    contentDescription = "photo_logo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.width(32.dp))

                Column(
                    modifier = Modifier.padding(start = 32.dp)
                ) {
                    Text(text = "Deskripsi", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Bayar WI-Fi", fontSize = 14.sp)
                }
            }
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.lihat_bukti)),
                    onClick = {},
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 6.dp)
                )
                Text(
                    text = "07/07/24",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessUploadScreenPreview() {
    FinansiapTheme {
        SuccessUploadScreen(rememberNavController())
    }
}