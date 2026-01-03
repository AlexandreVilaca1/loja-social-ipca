package com.example.sas.presentation.ui.Home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.sas.presentation.ui.appointments.AppointmentsView
import com.example.sas.presentation.ui.appointments.AgendamentosViewModel
import com.example.sas.presentation.ui.appointments.TotalAppointmentsView
import com.example.sas.presentation.ui.BottomBar.BottomBar
import com.example.sas.presentation.ui.BottomBar.BottomRoute
import com.example.sas.presentation.ui.beneficiaries.BeneficiariesView
import com.example.sas.presentation.ui.distributionItems.DistributionItemsView
import com.example.sas.presentation.ui.distributions.BeneficiaryDistributionsView

@Composable
fun HomeView() {
    var selectedIndex by remember { mutableStateOf(0) }
    val bottomNavController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomBar(bottomNavController)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController = bottomNavController,
                startDestination = BottomRoute.Agendamentos.route
            ) {

                navigation(
                    route = "agendamentos",
                    startDestination = "agendamentos/home"
                ) {
                    composable("agendamentos/home") { backStackEntry ->
                        val viewModel = hiltViewModel<AgendamentosViewModel>(backStackEntry)
                        AppointmentsView(bottomNavController, viewModel)
                    }

                    composable("agendamentos/todos") { backStackEntry ->
                        val viewModel = hiltViewModel<AgendamentosViewModel>(backStackEntry)
                        TotalAppointmentsView(bottomNavController, viewModel)
                    }
                }

                composable(BottomRoute.Beneficiarios.route) {
                    BeneficiariesView(
                        innerPadding = padding,
                        navController = bottomNavController
                    )
                }

                composable("beneficiary/{beneficiaryId}/distributions?name={beneficiaryName}") {
                    BeneficiaryDistributionsView(navController = bottomNavController)
                }

                composable("distribution/{distributionId}/items?date={distributionDate}") {
                    DistributionItemsView(navController = bottomNavController)
                }

                composable(BottomRoute.Doacoes.route) {
                    Text("Tela Doações")
                }

                composable(BottomRoute.Produtos.route) {
                    Text("Tela Produtos")
                }

                composable(BottomRoute.Relatorios.route) {
                    Text("Tela Relatórios")
                }

            }
        }
    }
}
