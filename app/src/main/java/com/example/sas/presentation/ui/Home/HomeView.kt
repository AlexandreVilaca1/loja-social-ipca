package com.example.sas.presentation.ui.Home

import DrawerContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.sas.presentation.ui.BottomBar.BottomBar
import com.example.sas.presentation.ui.appointments.AppointmentsView
import com.example.sas.presentation.ui.appointments.AppointmentViewModel
import com.example.sas.presentation.ui.appointments.TotalAppointmentsView
import com.example.sas.presentation.ui.Drawer.DrawerRoute
import com.example.sas.presentation.ui.auth.AuthViewModel
import com.example.sas.presentation.ui.beneficiaries.BeneficiariesView
import com.example.sas.presentation.ui.campaigns.list.CampaignsView
import com.example.sas.presentation.ui.campaigns.summary.CampaignDashboardView
import com.example.sas.presentation.ui.distributionItems.DistributionItemsView
import com.example.sas.presentation.ui.distributions.BeneficiaryDistributionsView
import com.example.sas.presentation.ui.reports.ReportsView
import com.example.sas.presentation.ui.stock.categories.CategoriesView
import com.example.sas.presentation.ui.stock.home.StockHomeView
import com.example.sas.presentation.ui.stock.lots.LotsView
import com.example.sas.presentation.ui.stock.products.ProductsView
import com.example.sas.presentation.ui.theme.GreenPrimary
import kotlinx.coroutines.launch
import com.example.sas.presentation.ui.candidacies.CandidaciesView
import com.example.sas.presentation.ui.donations.items.DonationItemsView
import com.example.sas.presentation.ui.donations.list.DonationsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(onSignOut : () -> Unit) {

    val navController = rememberNavController()

    val authViewModel: AuthViewModel = hiltViewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val shouldShowBackButton = currentRoute == "agendamentos/todos" ||
                                currentRoute?.startsWith("beneficiary/") == true && currentRoute.contains("/distributions") ||
                                currentRoute?.startsWith("distribution/") == true && currentRoute.contains("/items") ||
                                currentRoute == "donations/campaigns" ||
                                currentRoute == "donations/list" ||
                                currentRoute?.startsWith("donation/items/") == true // Voltar para a Lista de Doações

    val currentTitle = when {
        currentRoute == "agendamentos/home" -> "Agendamentos"
        currentRoute == "agendamentos/todos" -> "Lista de Distribuições"
        currentRoute?.startsWith("agendamentos") == true -> "Agendamentos"
        currentRoute == DrawerRoute.Beneficiarios.route -> "Beneficiários"
        currentRoute == DrawerRoute.Candidaturas.route -> "Candidaturas"
        currentRoute?.startsWith("beneficiary/") == true && currentRoute.contains("/distributions") -> "Distribuições"
        currentRoute?.startsWith("distribution/") == true && currentRoute.contains("/items") -> "Produtos da Distribuição"
        currentRoute == DrawerRoute.Doacoes.route -> "Doações"
        currentRoute?.startsWith("stock/") == true && currentRoute.contains("/home") -> "Stock"
        currentRoute?.startsWith("stock/") == true && currentRoute.contains("/products") -> "Produtos"
        currentRoute?.startsWith("stock/") == true && currentRoute.contains("/lots") -> "Lotes"
        currentRoute?.startsWith("stock/") == true && currentRoute.contains("/categories") -> "Categorias"
        currentRoute == DrawerRoute.Relatorios.route -> "Relatórios"
        else -> ""
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
                onDestinationClick = { route ->
                    if (route == "action_create_user") {
                        authViewModel.toggleCreateUserDialog(true) // Abre o diálogo
                        scope.launch { drawerState.close() } // Fecha o menu lateral
                    } else {
                        // Navegação normal para outras rotas
                        scope.launch { drawerState.close() }
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onSignOut = {onSignOut()},
                authViewModel = authViewModel
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

            CreateUserDialog(authViewModel = authViewModel)

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
                            val viewModel = hiltViewModel<AppointmentViewModel>(backStackEntry)
                            AppointmentsView(navController, viewModel)
                        }

                        composable("agendamentos/todos") { backStackEntry ->
                            val viewModel = hiltViewModel<AppointmentViewModel>(backStackEntry)
                            TotalAppointmentsView(navController, viewModel)
                        }
                    }

                    navigation(
                        route = "stock",
                        startDestination = "stock/home"
                    ){
                        composable("stock/home") {
                            StockHomeView(navController = navController)
                        }
                        composable("stock/lots") {
                            LotsView(navController = navController)
                        }
                        composable("stock/products") {
                            ProductsView(navController = navController)
                        }
                        composable("stock/categories"){
                            CategoriesView(navController = navController)
                        }
                    }

                    composable(DrawerRoute.Beneficiarios.route) {
                        BeneficiariesView(
                            innerPadding = padding,
                            navController = navController
                        )
                    }

                    composable(DrawerRoute.Candidaturas.route) {
                        CandidaciesView()
                    }

                    composable("beneficiary/{beneficiaryId}/distributions?name={beneficiaryName}") {
                        BeneficiaryDistributionsView(navController = navController)
                    }

                    composable("distribution/{distributionId}/items?date={distributionDate}") {
                        DistributionItemsView()
                    }

                    composable(DrawerRoute.Doacoes.route) {
                        CampaignDashboardView(navController)
                    }

                    composable("donations/campaigns") {
                        CampaignsView(navController)
                    }

                    composable("donations/list") {
                        DonationsView(navController)
                    }

                    composable("donation/items/{donationId}") {
                        DonationItemsView()
                    }

                    composable(DrawerRoute.Relatorios.route) {
                        ReportsView()
                    }
                }
            }
        }
    }
}

@Composable
fun CreateUserDialog(authViewModel: AuthViewModel) {
    val state by authViewModel.uiState.collectAsState()
    val form = state.newUserForm
    val context = LocalContext.current

    if (state.showCreateUserDialog) {
        AlertDialog(
            onDismissRequest = { authViewModel.toggleCreateUserDialog(false) },
            title = { Text("Registar Utilizador", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (state.createUserError != null) {
                        Text(state.createUserError!!, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                    }

                    OutlinedTextField(value = form.name, onValueChange = { authViewModel.onNewUserFormChange(name = it) }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = form.email, onValueChange = { authViewModel.onNewUserFormChange(email = it) }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = form.password, onValueChange = { authViewModel.onNewUserFormChange(password = it) }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())

                    Text("Tipo de Utilizador:", style = MaterialTheme.typography.labelMedium, color = Color.Gray)

                    // Seleção de Role entre SAS e Beneficiário
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("SAS", "Beneficiário").forEach { roleName ->
                            val isSelected = form.role == roleName
                            FilterChip(
                                selected = isSelected,
                                onClick = { authViewModel.onNewUserFormChange(role = roleName) },
                                label = { Text(roleName) },
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = GreenPrimary.copy(alpha = 0.2f),
                                    selectedLabelColor = GreenPrimary
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { authViewModel.createNewUser(context) },
                    enabled = !state.isCreatingUser,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    if (state.isCreatingUser) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Criar Conta")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { authViewModel.toggleCreateUserDialog(false) }) { Text("Cancelar") }
            }
        )
    }
}