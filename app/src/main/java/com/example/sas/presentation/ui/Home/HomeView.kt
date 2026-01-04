package com.example.sas.presentation.ui.Home

import DrawerContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.sas.presentation.ui.BottomBar.BottomBar
import com.example.sas.presentation.ui.appointments.AppointmentsView
import com.example.sas.presentation.ui.appointments.AgendamentosViewModel
import com.example.sas.presentation.ui.appointments.TotalAppointmentsView
import com.example.sas.presentation.ui.Drawer.DrawerRoute
import com.example.sas.presentation.ui.beneficiaries.BeneficiariesView
import com.example.sas.presentation.ui.distributionItems.DistributionItemsView
import com.example.sas.presentation.ui.distributions.BeneficiaryDistributionsView
import com.example.sas.presentation.ui.theme.GreenPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {

    val navController = rememberNavController()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val shouldShowBackButton = currentRoute?.startsWith("beneficiary/") == true && currentRoute.contains("/distributions") ||
                                currentRoute?.startsWith("distribution/") == true && currentRoute.contains("/items")

    val currentTitle = when {
        currentRoute?.startsWith("agendamentos") == true -> "Agendamentos"
        currentRoute == DrawerRoute.Beneficiarios.route -> "Beneficiários"
        currentRoute?.startsWith("beneficiary/") == true && currentRoute.contains("/distributions") -> "Distribuições"
        currentRoute?.startsWith("distribution/") == true && currentRoute.contains("/items") -> "Itens da Distribuição"
        currentRoute == DrawerRoute.Doacoes.route -> "Doações"
        currentRoute == DrawerRoute.Produtos.route -> "Produtos"
        currentRoute == DrawerRoute.Relatorios.route -> "Relatórios"
        else -> ""
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
                onDestinationClick = { route ->
                    scope.launch { drawerState.close() }

                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {

        Scaffold(
            bottomBar = {
                BottomBar(navController)
            },
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.height(56.dp),
                    title = {
                        Text(
                            text = currentTitle,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (shouldShowBackButton) {
                                navController.navigateUp()
                            } else {
                                scope.launch { drawerState.open() }
                            }
                        }) {
                            Icon(
                                imageVector = if (shouldShowBackButton) {
                                    Icons.AutoMirrored.Filled.ArrowBack
                                } else {
                                    Icons.Default.Menu
                                },
                                contentDescription = if (shouldShowBackButton) "Voltar" else "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = GreenPrimary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    windowInsets = WindowInsets(0,0,0,0)
                )
            }

        ) { padding ->

            Box(modifier = Modifier.padding(padding)) {

                NavHost(
                    navController = navController,
                    startDestination = DrawerRoute.Agendamentos.route
                ) {

                    navigation(
                        route = "agendamentos",
                        startDestination = "agendamentos/home"
                    ) {
                        composable("agendamentos/home") { backStackEntry ->
                            val viewModel = hiltViewModel<AgendamentosViewModel>(backStackEntry)
                            AppointmentsView(navController, viewModel)
                        }

                        composable("agendamentos/todos") { backStackEntry ->
                            val viewModel = hiltViewModel<AgendamentosViewModel>(backStackEntry)
                            TotalAppointmentsView(navController, viewModel)
                        }
                    }

                    composable(DrawerRoute.Beneficiarios.route) {
                        BeneficiariesView(
                            innerPadding = padding,
                            navController = navController
                        )
                    }

                    composable("beneficiary/{beneficiaryId}/distributions?name={beneficiaryName}") {
                        BeneficiaryDistributionsView(navController = navController)
                    }

                    composable("distribution/{distributionId}/items?date={distributionDate}") {
                        DistributionItemsView()
                    }

                    composable(DrawerRoute.Doacoes.route) {
                        Text("Tela Doações")
                    }

                    composable(DrawerRoute.Produtos.route) {
                        Text("Tela Produtos")
                    }

                    composable(DrawerRoute.Relatorios.route) {
                        Text("Tela Relatórios")
                    }
                }
            }
        }
    }
}
