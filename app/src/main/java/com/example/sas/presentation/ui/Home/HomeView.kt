package com.example.sas.presentation.ui.Home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sas.presentation.ui.Agendamentos.AgendamentosView
import com.example.sas.presentation.ui.Agendamentos.TodosAgendamentosView
import com.example.sas.presentation.ui.BottomBar.BottomBar
import com.example.sas.presentation.ui.BottomBar.BottomRoute

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
                composable(BottomRoute.Agendamentos.route) {
                    AgendamentosView(bottomNavController)
                }

                composable(BottomRoute.Beneficiarios.route) {
                    Text("Tela Beneficiários")
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
