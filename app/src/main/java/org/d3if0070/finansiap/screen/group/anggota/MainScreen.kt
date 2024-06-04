package org.d3if0070.finansiap.screen.group.anggota

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.R
import org.d3if0070.finansiap.component.BottomNavBar
import org.d3if0070.finansiap.firebase.GrupRepository
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.FinansiapTheme
import org.d3if0070.finansiap.ui.theme.Outline
import org.d3if0070.finansiap.viewmodel.GrupViewModel
import org.d3if0070.finansiap.model.Grup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAnggota(navController: NavHostController, grupId: String, viewModel: GrupViewModel) {
    val grup by viewModel.currentGrup.collectAsState()

    LaunchedEffect(grupId) {
        viewModel.getGrupById(grupId)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.ListGroup.route)
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
        grup?.let { grupData ->
            ScreenContent(modifier = Modifier.padding(innerPadding), navController, grupData)
        }
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController, grup: Grup) {
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        // Bagian Tagihan
        if (grup.tagihan.isNotEmpty()) {
            TagihanBox(tagihan = grup.tagihan)
        } else {
            DefaultMessageBox(message = "Tagihan tidak tersedia")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Bagian Deskripsi
        if (grup.deskripsi.isNotEmpty()) {
            DeskripsiBox(deskripsi = grup.deskripsi)
        } else {
            DefaultMessageBox(message = "Deskripsi tidak tersedia")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
                    contentDescription = "upload"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

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
fun TagihanBox(tagihan: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(width = 2.dp, color = Outline),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = tagihan,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun DeskripsiBox(deskripsi: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(width = 2.dp, color = Outline),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = deskripsi,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun DefaultMessageBox(message: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Gray
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val viewModel = GrupViewModel(GrupRepository())
    MainScreenAnggota(navController, "sample_grup_id", viewModel)
}
