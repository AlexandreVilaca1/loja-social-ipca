package com.example.sas.presentation.ui.Home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sas.presentation.ui.Apontamentos.AgendamentosView
import com.example.sas.presentation.ui.Apontamentos.TodosAgendamentosView
import com.example.sas.presentation.ui.beneficiaries.BeneficiariesView
import com.example.sas.presentation.ui.distributions.BeneficiaryDistributionsView
import com.example.sas.presentation.ui.distributionItems.DistributionItemsView
import com.example.sas.presentation.ui.BottomBar.BottomBar
import com.example.sas.presentation.ui.BottomBar.BottomRoute

@Composable
fun HomeView() {
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
                composable(BottomRoute.Agendamentos.route) {
                    AgendamentosView(bottomNavController)
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

                composable("agendamentos/todos") {
                    TodosAgendamentosView()
                }
            }
        }
    }
}
