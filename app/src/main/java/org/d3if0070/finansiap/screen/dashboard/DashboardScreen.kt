package org.d3if0070.finansiap.screen.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import org.d3if0070.finansiap.viewmodel.GrupViewModel

@Composable
fun DashboardScreen(navController: NavHostController, viewModel: GrupViewModel, userEmail: String) {
    val joinedGrupList by viewModel.joinedGrupList.collectAsState()
    val createdGrupList by viewModel.createdGrupList.collectAsState()
    val tagihanList by viewModel.tagihanList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchGrupData(userEmail)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.Dashboard.route)
        },
        containerColor = Color.White
    ) {
        ScreenContent(
            modifier = Modifier.padding(it),
            navController = navController,
            joinedGrupList = joinedGrupList,
            createdGrupList = createdGrupList,
            tagihanList = tagihanList
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    joinedGrupList: List<Grup>,
    createdGrupList: List<Grup>,
    tagihanList: List<String>
) {
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        // Section for upcoming bills
        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.tagihan_mendatang),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = BackgroundBar
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (tagihanList.isEmpty()) {
            DefaultMessageBox(message = "Tidak ada tagihan mendatang")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(width = 2.dp, color = Outline),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                tagihanList.forEach { tagihan ->
                    TagihanBox(tagihan = tagihan)
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally),
                        thickness = 1.dp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Section for joined groups
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.list_grup),
            style = MaterialTheme.typography.titleMedium,
            color = BackgroundBar,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))

        if (joinedGrupList.isEmpty() && createdGrupList.isEmpty()) {
            DefaultMessageBox(message = "Tidak ada grup yang terdaftar")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(width = 2.dp, color = Outline),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                joinedGrupList.forEach { grup ->
                    GrupCard(grup = grup, navController = navController)
                }
                createdGrupList.forEach { grup ->
                    GrupCard(grup = grup, navController = navController)
                }
            }
        }
    }
}

@Composable
fun TagihanBox(tagihan: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = tagihan,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun GrupCard(grup: Grup, navController: NavHostController) {
    OutlinedButton(
        onClick = {
            val route = if (grup.bendahara == FirebaseAuth.getInstance().currentUser?.email) {
                Screen.MainScreenBendahara.createRoute(grup.gid)
            } else {
                Screen.MainScreenAnggota.createRoute(grup.gid)
            }
            navController.navigate(route)
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        border = BorderStroke(color = Outline, width = 2.dp),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = grup.namaGrup,
            color = BackgroundBar,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
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
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    val viewModel = GrupViewModel(GrupRepository())
    DashboardScreen(navController, viewModel, "user@example.com")
}
