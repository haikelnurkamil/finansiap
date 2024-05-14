package org.d3if0070.finansiap.screen

import android.content.res.Configuration
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline

@Composable
fun AccountScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(bottomBar = {
        BottomNavBar(navController = navController, Screen.Account.route)
    },
        containerColor = Color.White) {
        ScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    var nama by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Account",
            style = MaterialTheme.typography.headlineLarge,
            color = BackgroundBar
        )

        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text(text = "Nama") },
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline
            ),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            singleLine = true,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth(0.8f),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {  },
            border = BorderStroke(2.dp, Color.Transparent),
            modifier = Modifier.fillMaxWidth(0.4f),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                containerColor = Color.Green
            )
        ) {
            Text("Simpan")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { },
            border = BorderStroke(2.dp, Color.Transparent),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                containerColor = Color.Red
            ),
            modifier = Modifier.fillMaxWidth(0.4f)
        ) {
            Text("Keluar")
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AccountScreenPreview() {
    FinansiapTheme {
        AccountScreen(rememberNavController())
    }
}