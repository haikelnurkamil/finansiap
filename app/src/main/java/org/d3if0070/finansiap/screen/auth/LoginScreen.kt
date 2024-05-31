
package org.d3if0070.finansiap.screen.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline
import org.d3if0070.finansiap.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: UserViewModel
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val loginError by viewModel.loginError.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            Toast.makeText(context, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Dashboard.route)
        }
    }

    LaunchedEffect(loginError) {
        loginError?.let {
            Toast.makeText(context, "Gagal Masuk: $it", Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_finansiap),
            contentDescription = "login_image",
            modifier = Modifier
                .size(350.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Alamat Surel") },
                singleLine = true,
                isError = emailError,
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Outline,
                    focusedBorderColor = Outline
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = RoundedCornerShape(24.dp)
            )
            if (emailError) {
                Text(text = "Alamat Surel tidak boleh kosong", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Kata Sandi") },
                singleLine = true,
                isError = emailError,
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Outline,
                    focusedBorderColor = Outline
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisible) painterResource(id = R.drawable.baseline_visibility_24)
                        else  painterResource(id = R.drawable.baseline_visibility_off_24)

                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            painter = image,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = RoundedCornerShape(24.dp)
            )
            if (emailError) {
                Text(text = "Kata Sandi tidak boleh kosong", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    emailError = email.isEmpty()
                    passwordError = password.isEmpty()

                    if (!emailError && !passwordError) {
                        viewModel.loginUser(email, password)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Outline
                ),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(top = 10.dp)
            ) {
                Text("Masuk")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text(text = "Belum memiliki akun?", fontSize = 14.sp, modifier = Modifier)
            Spacer(modifier = Modifier.padding(2.dp))
            ClickableText(
                text = AnnotatedString("Daftar"),
                onClick = {
                    navController.navigate("registerScreen")
                },
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    FinansiapTheme {
        LoginScreen(rememberNavController(), viewModel())
    }
}