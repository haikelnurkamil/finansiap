package org.d3if0070.finansiap.screen.group.bendahara

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.firebase.GrupRepository
import org.d3if0070.finansiap.model.Grup
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline
import org.d3if0070.finansiap.viewmodel.GrupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenBendahara(navController: NavHostController, grupId: String, viewModel: GrupViewModel) {
    val grup by viewModel.currentGrup.collectAsState()

    LaunchedEffect(grupId) {
        viewModel.getGrupById(grupId)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.Group.route)
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(text = grup?.namaGrup ?: "")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = BackgroundBar,
                    titleContentColor = Color.White,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Menu.route + "/$grupId")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = stringResource(R.string.menu_grup),
                            tint = Color.White
                        )
                    }
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        grup?.let {
            ScreenContent(modifier = Modifier.padding(innerPadding), navController, it, viewModel)
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    grup: Grup,
    viewModel: GrupViewModel
) {
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        EditableTextBox(
            label = "Tagihan",
            value = grup.tagihan,
            onSave = { newValue ->
                viewModel.updateGrup(grup.copy(tagihan = newValue))
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        EditableTextBox(
            label = "Deskripsi",
            value = grup.deskripsi,
            onSave = { newValue ->
                viewModel.updateGrup(grup.copy(deskripsi = newValue))
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { navController.navigate(Screen.DetailScreen.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = BorderStroke(color = Outline, width = 2.dp),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = stringResource(R.string.lihat_bukti_pembayaran),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { navController.navigate(Screen.FormUpload.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(color = Outline, width = 2.dp),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.upload_icon),
                    contentDescription = "upload")
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Unggah Bukti Transfer",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EditableTextBox(label: String, value: String, onSave: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }
    var editing by remember { mutableStateOf(false) }

    if (editing) {
        Column {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(label) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onSave(text)
                    editing = false
                })
            )
            Row {
                Button(onClick = {
                    onSave(text)
                    editing = false
                }) {
                    Text("Save")
                }
                Button(onClick = { editing = false }) {
                    Text("Cancel")
                }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(width = 2.dp, color = Outline), shape = RoundedCornerShape(20.dp))
                .padding(16.dp)
                .clickable { editing = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label: $value",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit $label"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val viewModel = GrupViewModel(GrupRepository())
    MainScreenBendahara(rememberNavController(), "sample_grup_id", viewModel)
}
