package org.d3if0070.finansiap.screen.account

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline
import org.d3if0070.finansiap.viewmodel.UserViewModel

@Composable
fun AccountScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.Account.route)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController, viewModel())
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: UserViewModel
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val currentUser by viewModel.currentUser.collectAsState()

    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var readOnly by remember { mutableStateOf(true) }

    var passwordVisibility by remember { mutableStateOf(true) }

    val updateSuccess by viewModel.updateSuccess.collectAsState()

    val updateError by viewModel.updateError.collectAsState()

    LaunchedEffect(Unit) {
        val userId = viewModel.currentUser.value?.uid
        if (userId != null) {
            viewModel.fetchUserData(userId)
        }
    }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            username = it.userName
            email = it.email
            password = it.password
        }
    }

    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            Toast.makeText(context, "Berhasil Tersimpan", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Account.route) { inclusive = true }
            }
            viewModel.resetUpdateState()
        }
    }

    LaunchedEffect(updateError) {
        updateError?.let {
            Toast.makeText(context, "Gagal Menyimpan: $it", Toast.LENGTH_SHORT).show()
            viewModel.resetUpdateState()
        }
    }

    LaunchedEffect(viewModel.logoutSuccess) {}
    val logoutSuccess =viewModel.logoutSuccess.collectAsState().value
    if (logoutSuccess) {
        Toast.makeText(context, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
        viewModel.resetLogoutState()
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
            value = username,
            singleLine = true,
            onValueChange = { username = it },
            readOnly = readOnly,
            shape = RoundedCornerShape(24.dp),
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
            readOnly = readOnly,
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            readOnly = readOnly,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Outline,
                focusedBorderColor = Outline
            ),
            trailingIcon = {
                val image =
                    if (passwordVisibility) painterResource(id = R.drawable.baseline_visibility_24)
                    else painterResource(id = R.drawable.baseline_visibility_off_24)

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = image,
                        contentDescription = if (passwordVisibility) "Hide Password" else "Show Password"
                    )
                }
            },
            visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

//        Button(
//            onClick = { showDialog = true },
//            modifier = Modifier.fillMaxWidth(0.4f),
//            colors = ButtonDefaults.outlinedButtonColors(
//                contentColor = Color.White,
//                containerColor = Color.Green
//            )
//        ) {
//            Text("Simpan")
//        }

        Button(
            onClick = {
                if (readOnly) {
                    readOnly = false
                } else {
                    viewModel.update(username, email, password, onSuccess = {
                        readOnly = true
                    }, onFailure = {
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    })
                }
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Green
            ),
            modifier = Modifier.fillMaxWidth(0.4f),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = stringResource(if (readOnly) R.string.bttn_ubah else R.string.bttn_simpan),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.logout(navController) },
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
@Composable
fun AccountScreenPreview() {
    FinansiapTheme {
        AccountScreen(rememberNavController())
    }
}