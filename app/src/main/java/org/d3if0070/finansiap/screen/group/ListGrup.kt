package org.d3if0070.finansiap.screen.group

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun ListGroupScreen(navController: NavHostController) {
    val repository = GrupRepository()
    val factory = GrupViewModelFactory(repository)
    val viewModel: GrupViewModel = viewModel(factory = factory)
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: "unknown"

    LaunchedEffect(Unit) {
        viewModel.fetchJoinedGrup(userEmail)
        viewModel.fetchCreatedGrup(userEmail)
    }

    val joinedGrupList by viewModel.joinedGrupList.collectAsState()
    val createdGrupList by viewModel.createdGrupList.collectAsState()
    val grupList = joinedGrupList + createdGrupList

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, Screen.ListGroup.route)
        },
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Group.route)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.group),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        ScreenContent(modifier = Modifier.padding(it), navController, grupList, userEmail)
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    grupList: List<Grup>,
    userId: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .padding(36.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.list_grup),
            style = MaterialTheme.typography.titleLarge,
            color = BackgroundBar,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(grupList) { grup ->
                        GrupCard(grup = grup, navController = navController, userId = userId)
                    }
                }
            }
        }
    }
}

@Composable
fun GrupCard(
    grup: Grup,
    navController: NavHostController,
    userId: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(2.dp, Outline, RoundedCornerShape(10.dp))
            .clickable {
                if (grup.bendahara == userId) {
                    navController.navigate(Screen.MainScreenBendahara.createRoute(grup.gid))
                } else {
                    navController.navigate(Screen.MainScreenAnggota.createRoute(grup.gid))
                }
            },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp).fillMaxWidth(0.8f),
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
fun ListGroupScreenPreview() {
    FinansiapTheme {
        ListGroupScreen(rememberNavController())
    }
}
