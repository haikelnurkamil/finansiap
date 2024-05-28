package org.d3if0070.finansiap.screen.group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
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
fun MenuScreen(navController: NavHostController) {
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
                    Text(text = (stringResource(id = R.string.anggota_grup)))
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

@Composable
private fun ScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "4 Anggota",
            color = BackgroundBar,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(width = 2.dp, color = Outline),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp),
        ) {
            Row {
                Text(
                    text = "Haikel Nur Kamil",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Image(
                    painter = painterResource(id = R.drawable.key_logo),
                    contentDescription = "bendahara")
            }

            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Muhammad Bintang",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Rayhan Fahrezy",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Ficky Chikara",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 1.dp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    FinansiapTheme {
        MenuScreen(rememberNavController())
    }
}