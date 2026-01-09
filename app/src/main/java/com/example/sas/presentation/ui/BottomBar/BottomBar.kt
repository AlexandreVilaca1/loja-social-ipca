package com.example.sas.presentation.ui.BottomBar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sas.presentation.ui.Drawer.DrawerRoute
import com.example.sas.presentation.ui.auth.AuthViewModel
import com.example.sas.presentation.ui.theme.GreenPrimary
import com.example.sas.presentation.ui.theme.GrayInactive

data class BottomBarItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomBar(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.uiState.collectAsState()
    val userRole = authState.currentUser?.role

    // 1. Lista base de itens
    val allItems = listOf(
        BottomBarItem("Agendamentos", Icons.Outlined.Event, DrawerRoute.Agendamentos.route),
        BottomBarItem("Beneficiários", Icons.Outlined.People, DrawerRoute.Beneficiarios.route),
        BottomBarItem("Doações", Icons.Outlined.FavoriteBorder, DrawerRoute.Doacoes.route),
        BottomBarItem("Produtos", Icons.Outlined.Inventory2, DrawerRoute.Produtos.route),
        BottomBarItem("Relatórios", Icons.Outlined.Description, DrawerRoute.Relatorios.route)
    )

    // 2. Filtramos os itens com base na Role
    val filteredItems = remember(userRole) {
        if (userRole == "SAS") {
            allItems // SAS vê a barra de gestão (5 itens)
        } else if (userRole == "Beneficiário") {
            // Beneficiário vê Agendamentos, Produtos e agora Pedidos
            listOf(
                allItems[0], // Agendamentos
                allItems[3], // Produtos
                BottomBarItem("Pedidos", Icons.Outlined.Assignment, DrawerRoute.Pedidos.route)
            )
        } else {
            emptyList()
        }
    }

    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    // 3. Mapeamento da rota selecionada para manter o ícone aceso
    val selectedRoute = when {
        currentRoute?.startsWith("agendamentos") == true -> DrawerRoute.Agendamentos.route
        currentRoute?.startsWith("beneficiarios") == true -> DrawerRoute.Beneficiarios.route
        currentRoute?.startsWith("donations") == true -> DrawerRoute.Doacoes.route
        currentRoute?.startsWith("stock") == true -> DrawerRoute.Produtos.route
        currentRoute == DrawerRoute.Relatorios.route -> DrawerRoute.Relatorios.route
        currentRoute == DrawerRoute.Pedidos.route -> DrawerRoute.Pedidos.route // Adicionado
        else -> null
    }

    NavigationBar(
        containerColor = Color.White,
        windowInsets = WindowInsets(0, 0, 0, 0),
        tonalElevation = 0.dp
    ) {
        filteredItems.forEach { item ->
            val selected = selectedRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    when (item.route) {
                        DrawerRoute.Agendamentos.route -> {
                            // Sempre vai para agendamentos/home e limpa a pilha
                            navController.navigate("agendamentos/home") {
                                popUpTo("agendamentos") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                        DrawerRoute.Produtos.route -> {
                            // Sempre vai para stock/home e limpa a pilha
                            navController.navigate("stock/home") {
                                popUpTo("stock") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                        else -> {
                            // Outras rotas navegam normalmente
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) GreenPrimary else GrayInactive
                    )
                },
                label = null, // Mantendo o estilo original sem labels
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Evita o círculo de fundo do M3
                )
            )
        }
    }
}