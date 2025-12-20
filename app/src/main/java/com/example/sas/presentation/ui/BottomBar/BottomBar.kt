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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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
        BottomBarItem("Agendamentos", Icons.Outlined.Event, BottomRoute.Agendamentos.route),
        BottomBarItem("Beneficiários", Icons.Outlined.People, BottomRoute.Beneficiarios.route),
        BottomBarItem("Doações", Icons.Outlined.FavoriteBorder, BottomRoute.Doacoes.route),
        BottomBarItem("Produtos", Icons.Outlined.Inventory2, BottomRoute.Produtos.route),
        BottomBarItem("Relatórios", Icons.Outlined.Description, BottomRoute.Relatorios.route)
    )

    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    val selectedRoute = when {
        currentRoute?.startsWith("agendamentos") == true ->
            BottomRoute.Agendamentos.route

        currentRoute == BottomRoute.Beneficiarios.route ->
            BottomRoute.Beneficiarios.route

        currentRoute == BottomRoute.Doacoes.route ->
            BottomRoute.Doacoes.route

        currentRoute == BottomRoute.Produtos.route ->
            BottomRoute.Produtos.route

        currentRoute == BottomRoute.Relatorios.route ->
            BottomRoute.Relatorios.route

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
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (selected) GreenPrimary else GrayInactive
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) GreenPrimary else GrayInactive
                    )
                }
            )
        }
    }
}
