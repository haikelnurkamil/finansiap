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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import org.d3if0070.finansiap.model.Grup
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline
import org.d3if0070.finansiap.util.GrupViewModelFactory
import org.d3if0070.finansiap.viewmodel.GrupViewModel

@Composable
fun CreateScreen(navController: NavHostController) {
    Scaffold(bottomBar = {
        BottomNavBar(navController = navController, Screen.Group.route)
    },
        containerColor = Color.White) {
        CreateGroup(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun CreateGroup(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val repository = GrupRepository()
    val factory = GrupViewModelFactory(repository)
    val viewModel: GrupViewModel = viewModel(factory = factory)

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: "unknown"

    var namaGrupError by remember { mutableStateOf(false) }
    var kodeGrupError by remember { mutableStateOf(false) }

    var nama by remember { mutableStateOf("") }
    var kode by remember { mutableStateOf("") }

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
            text = "Buat Grup",
            style = MaterialTheme.typography.headlineLarge,
            color = BackgroundBar
        )

        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(
            value = nama,
            singleLine = true,
            onValueChange = { nama = it },
            placeholder = { Text(text = "Masukan Nama Grup") },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        if (namaGrupError) {
            Text(text = "Nama Grup tidak boleh kosong", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = kode,
            singleLine = true,
            onValueChange = { kode = it },
            placeholder = { Text(text = "Buat Kode Undangan") },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        )
        if (kodeGrupError) {
            Text(text = "Kode Grup tidak boleh kosong", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = {
                namaGrupError = nama.isEmpty()
                kodeGrupError = kode.isEmpty()

                if (namaGrupError || kodeGrupError) {
                    return@OutlinedButton
                } else {
                    viewModel.insert(nama, kode, userEmail)
                    navController.navigate(Screen.ListGroup.route)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Outline),
            border = BorderStroke(color = Outline, width = 1.dp),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp),
        ) {
            Text(text = "Buat",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    FinansiapTheme {
        CreateScreen(rememberNavController())
    }
}