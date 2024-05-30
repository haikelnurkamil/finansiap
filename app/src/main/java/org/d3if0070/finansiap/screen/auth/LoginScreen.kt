package org.d3if0070.finansiap.screen.auth

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.Alert.AlertDialog
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.screen.auth.data.component.PasswordMyTextFieldComponent
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline

@Composable
fun LoginScreen(loginViewModel: LoginViewModel? = null, navController: NavHostController) {

    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
//    val context = LocalContext.current


    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog){
        AlertDialog (onDismiss = {showDialog=false})
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
            modifier = Modifier.padding(32.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                singleLine = true,
                label = {
                    Text(text = "Email")
                },
                isError = isError,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Outline
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            PasswordMyTextFieldComponent(
                labelValue = "",
                onTextSelected = {

                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = { navController.navigate("dashboardScreen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(color = Outline, width = 1.dp),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(50.dp),
            ) {
                Text(text = "Masuk",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(153
            .dp))

        Row {
            Text(
                text = "Belum punya akun?", fontSize = 14.sp, modifier = Modifier
                    .padding(bottom = 30.dp)
            )
            Spacer(modifier = Modifier.padding(2.dp))
            ClickableText(
                text = AnnotatedString("Daftar"),
                onClick = {
                          navController.navigate("registerScreen")
                },
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    FinansiapTheme {
        LoginScreen(LoginViewModel() ,rememberNavController())
    }
}