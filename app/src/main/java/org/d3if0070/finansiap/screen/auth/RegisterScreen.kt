package org.d3if0070.finansiap.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.screen.auth.data.RegisterUIEvent
import org.d3if0070.finansiap.screen.auth.data.RegisterViewModel
import org.d3if0070.finansiap.screen.auth.data.component.ButtonComponent
import org.d3if0070.finansiap.screen.auth.data.component.MyTextFieldComponent
import org.d3if0070.finansiap.screen.auth.data.component.PasswordMyTextFieldComponent
import org.d3if0070.finansiap.ui.theme.FinansiapTheme

@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = viewModel()
) {
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
            MyTextFieldComponent(
                labelValue = "Nama Pengguna",
                onTextSelected = {
                    registerViewModel.onEvent(RegisterUIEvent.UserNameChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            MyTextFieldComponent(
                labelValue = "Email",
                onTextSelected = {
                    registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            PasswordMyTextFieldComponent(
                labelValue = "Password",
                onTextSelected = {
                    registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            ButtonComponent(
                value = "Buat",
                onButtonClicked = { registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked) },
                isEnabled = registerViewModel.allValidationsPassed.value,
                navController
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(
                text = "Sudah memiliki akun?", fontSize = 14.sp, modifier = Modifier)
            Spacer(modifier = Modifier.padding(2.dp))
            ClickableText(
                text = AnnotatedString("Masuk"),
                onClick = {
                    navController.navigate("loginScreen")
                },
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    FinansiapTheme {
        RegisterScreen(rememberNavController())
    }
}