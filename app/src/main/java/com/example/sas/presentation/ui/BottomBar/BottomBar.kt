package com.example.sas.presentation.ui.BottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sas.presentation.ui.Drawer.DrawerRoute
import com.example.sas.presentation.ui.theme.GreenPrimary
import com.example.sas.presentation.ui.theme.GrayInactive


data class BottomBarItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)


@Composable
fun BottomBar(navController: NavController) {

    val items = listOf(
        BottomBarItem("Agendamentos", Icons.Outlined.Event, DrawerRoute.Agendamentos.route),
        BottomBarItem("Beneficiários", Icons.Outlined.People, DrawerRoute.Beneficiarios.route),
        BottomBarItem("Doações", Icons.Outlined.FavoriteBorder, DrawerRoute.Doacoes.route),
        BottomBarItem("Produtos", Icons.Outlined.Inventory2, DrawerRoute.Produtos.route),
        BottomBarItem("Relatórios", Icons.Outlined.Description, DrawerRoute.Relatorios.route)
    )

    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    val selectedRoute = when {
        currentRoute?.startsWith("agendamentos") == true ->
            DrawerRoute.Agendamentos.route

        currentRoute == DrawerRoute.Beneficiarios.route ->
            DrawerRoute.Beneficiarios.route

        currentRoute == DrawerRoute.Doacoes.route ->
            DrawerRoute.Doacoes.route

        currentRoute == DrawerRoute.Produtos.route ->
            DrawerRoute.Produtos.route

        currentRoute == DrawerRoute.Relatorios.route ->
            DrawerRoute.Relatorios.route

        else -> null
    }
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val selected = selectedRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    // Para rotas com sub-navegação (agendamentos e stock),
                    // navegar diretamente para a home delas
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
                        // Não restaurar estado para rotas com sub-navegação
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