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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.Alert.AlertDialog
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline

@Composable
fun JoinScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(bottomBar = {
        BottomNavBar(navController = navController, Screen.Group.route)
    },
        containerColor = Color.White) {
        JoinGroup(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun JoinGroup(modifier: Modifier, navController: NavHostController) {
    var kode by remember {
        mutableStateOf("")
    }
        IconButton(onClick = {navController.popBackStack()}) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.kembali),
                tint = BackgroundBar
            )
        }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog){
        AlertDialog (onDismiss = {showDialog=false})
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gabung Grup",
            style = MaterialTheme.typography.headlineLarge,
            color = BackgroundBar
        )

        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(
            value = kode,
            singleLine = true,
            onValueChange = { kode = it },
            placeholder = { Text(text = "Masukan kode Undangan") },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = { navController.navigate("listGroupScreen") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(color = Outline, width = 1.dp),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp),
        ) {
            Text(text = "Konfirmasi",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JoinScreenPreview() {
    FinansiapTheme {
        JoinScreen(rememberNavController())
    }
}