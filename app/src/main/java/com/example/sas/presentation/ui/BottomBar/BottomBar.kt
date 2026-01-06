package com.example.sas.presentation.ui.BottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.example.sas.presentation.ui.auth.AuthViewModel // Importar o seu AuthViewModel
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
    authViewModel: AuthViewModel = hiltViewModel() // Injetar o ViewModel
) {
    val authState by authViewModel.uiState.collectAsState()
    val userRole = authState.currentUser?.role

    // 1. Definimos todos os itens possíveis
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
            allItems // SAS vê tudo
        } else {
            // Outros (Beneficiários) vêem apenas Agendamentos e Produtos
            allItems.filter {
                it.route == DrawerRoute.Agendamentos.route || it.route == DrawerRoute.Produtos.route
            }
        }
    }

    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    val selectedRoute = when {
        currentRoute?.startsWith("agendamentos") == true -> DrawerRoute.Agendamentos.route
        currentRoute?.startsWith("beneficiarios") == true -> DrawerRoute.Beneficiarios.route
        currentRoute?.startsWith("doacoes") == true -> DrawerRoute.Doacoes.route
        currentRoute?.startsWith("stock") == true -> DrawerRoute.Produtos.route
        currentRoute == DrawerRoute.Relatorios.route -> DrawerRoute.Relatorios.route
        else -> null
    }

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        // 3. Iteramos apenas sobre a lista filtrada
        filteredItems.forEach { item ->
            val selected = selectedRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    val targetRoute = when (item.route) {
                        DrawerRoute.Agendamentos.route -> "agendamentos/home"
                        DrawerRoute.Produtos.route -> "stock/home"
                        else -> item.route
                    }

                    navController.navigate(targetRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = item.route != DrawerRoute.Agendamentos.route &&
                                item.route != DrawerRoute.Produtos.route
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (selected) GreenPrimary else GrayInactive
                    )
                },
                label = {}
            )
        }
    }
}