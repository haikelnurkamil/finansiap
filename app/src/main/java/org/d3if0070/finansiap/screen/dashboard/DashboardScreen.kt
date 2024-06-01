package org.d3if0070.finansiap.screen.dashboard

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import org.d3if0070.finansiap.util.GrupViewModelFactory
import org.d3if0070.finansiap.viewmodel.GrupViewModel


@Composable
fun DashboardScreen(navController: NavHostController) {
    val repository = GrupRepository()
    val factory = GrupViewModelFactory(repository)
    val viewModel: GrupViewModel = viewModel(factory = factory)

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.Dashboard.route)
        },
        containerColor = Color.White
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController)
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController : NavHostController, id: String? = null
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val repository = GrupRepository()
    val factory = GrupViewModelFactory(repository)
    val viewModel: GrupViewModel = viewModel(factory = factory)

    var namaGrup by rememberSaveable { mutableStateOf("") }
    var code by rememberSaveable { mutableStateOf("") }

    val grupList by viewModel.gruplist.collectAsState()

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getGrupById(id)
        data?.let {
            namaGrup = it.namaGrup
            code = it.kodeGrup
        }
    }
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(width = 2.dp, color = Outline),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    text = stringResource(R.string.nominal),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                thickness = 1.dp)

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.row_1),
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.row_2),
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

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
            if (grupList.isEmpty())
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.list_kosong))
                }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    items(grupList){grup ->
                        GrupCard(
                            grup = grup,
                            navController
                        )
                    }
                }
            }
//            OutlinedButton(
//                onClick = {navController.navigate("mainScreen1")},
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
//                border = BorderStroke(color = Outline, width = 2.dp),
//                shape = RoundedCornerShape(10.dp),
//                modifier = Modifier
//                    .fillMaxWidth(1f)
//                    .height(60.dp),
//            ) {
//                Text(
//                    modifier =  Modifier.fillMaxWidth(),
//                    text = "Grup 1",
//                    color = BackgroundBar,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
        }
    }
}
@Composable
fun GrupCard (
    grup: Grup,
    navController: NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(2.dp, Outline, RoundedCornerShape(10.dp))
            .clickable { navController.navigate(Screen.MainScreenBendahara.route) },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth(0.8f),
            text = grup.namaGrup,
            color = BackgroundBar,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
}



@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    FinansiapTheme {
        DashboardScreen(rememberNavController())
    }
}
