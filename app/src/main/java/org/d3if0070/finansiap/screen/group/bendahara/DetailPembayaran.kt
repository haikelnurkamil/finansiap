package org.d3if0070.finansiap.screen.group.bendahara

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
fun DetailScreen(navController: NavHostController, modifier: Modifier = Modifier) {
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
                    Text(text = (stringResource(id = R.string.detail_pembayaran)))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = BackgroundBar,
                    titleContentColor = Color.White,
                ),
            )
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Yang Telah Mengunggah",
            color = BackgroundBar,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(color = Outline, width = 2.dp),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    text = "Rayhan Fahrezy",
                    color = BackgroundBar,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.green_ellipse),
                    contentDescription = "green_ellipse",
                    modifier = Modifier.size(18.dp),
                    alignment = Alignment.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {navController.navigate("approvalScreen")},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(color = Outline, width = 2.dp),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    text = "Muhammad Bintang",
                    color = BackgroundBar,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.red_ellipse),
                    contentDescription = "red_ellipse",
                    modifier = Modifier.size(18.dp),
                    alignment = Alignment.Center
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Yang Belum Mengunggah",
            color = BackgroundBar,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium
        )
        OutlinedButton(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(color = Outline, width = 2.dp),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = "Ficky Chikara",
                color = BackgroundBar,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.yellow_ellipse),
                contentDescription = "yellow_ellipse",
                modifier = Modifier.size(20.dp),
                alignment = Alignment.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    FinansiapTheme {
        DetailScreen(rememberNavController())
    }
}