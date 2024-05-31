
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
import org.d3if0070.finansiap.screen.auth.data.LoginUIEvent
import org.d3if0070.finansiap.screen.auth.data.LoginViewModel
import org.d3if0070.finansiap.screen.auth.data.component.ButtonComponent
import org.d3if0070.finansiap.screen.auth.data.component.MyTextFieldComponent
import org.d3if0070.finansiap.screen.auth.data.component.PasswordMyTextFieldComponent
import org.d3if0070.finansiap.ui.theme.FinansiapTheme

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
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
                labelValue = "Email",
                onTextSelected = {
                    loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            PasswordMyTextFieldComponent(
                labelValue = "Password",
                onTextSelected = {
                    loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ButtonComponent(
                value = "Masuk",
                onButtonClicked = { loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked) },
                isEnabled = loginViewModel.allValidationsPassed.value,
                navController
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

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
        LoginScreen(rememberNavController())
    }
}