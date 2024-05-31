package org.d3if0070.finansiap.screen.group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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


@Composable
fun ListGroupScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.Group.route)
        },
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Group.route)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.group),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .padding(36.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.list_grup),
            style = MaterialTheme.typography.titleLarge,
            color = BackgroundBar,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = {navController.navigate("mainScreen1")},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(color = Outline, width = 2.dp),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(60.dp),
            ) {
                Text(
                    modifier =  Modifier.fillMaxWidth(),
                    text = "Grup 1",
                    color = BackgroundBar,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = {navController.navigate("mainScreen2")},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(color = Outline, width = 2.dp),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(60.dp),
            ) {
                Text(
                    modifier =  Modifier.fillMaxWidth(),
                    text = "Grup 1",
                    color = BackgroundBar,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListGroupScreenPreview() {
    FinansiapTheme {
        ListGroupScreen(rememberNavController())
    }
}
