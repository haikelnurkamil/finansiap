package org.d3if0070.finansiap.screen.group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.firebase.GrupRepository
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline
import org.d3if0070.finansiap.util.GrupViewModelFactory
import org.d3if0070.finansiap.viewmodel.GrupViewModel
import org.d3if0070.finansiap.model.User // Import User data class

@Composable
fun JoinScreen(navController: NavHostController) {
    Scaffold(bottomBar = {
        BottomNavBar(navController = navController, Screen.Group.route)
    },
        containerColor = Color.White) {
        JoinGroup(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun JoinGroup(modifier: Modifier, navController: NavHostController) {
    var kode by remember { mutableStateOf("") }
    var joinFailed by remember { mutableStateOf(false) }

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: "unknown"

    val repository = GrupRepository()
    val factory = GrupViewModelFactory(repository)
    val viewModel: GrupViewModel = viewModel(factory = factory)

    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.kembali),
            tint = BackgroundBar
        )
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
            onClick = {
                viewModel.joinGroup(userEmail, kode, {
                    navController.navigate(Screen.ListGroup.route)
                }, {
                    joinFailed = true
                })
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(color = Outline, width = 1.dp),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp),
        ) {
            Text(
                text = "Konfirmasi",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        if (joinFailed) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Failed to join the group. Please check the code and try again.",
                color = Color.Red
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
