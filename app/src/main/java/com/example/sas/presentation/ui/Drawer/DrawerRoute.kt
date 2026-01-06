package com.example.sas.presentation.ui.Drawer

sealed class DrawerRoute(val route: String) {
    object Agendamentos : DrawerRoute("agendamentos")
    object Beneficiarios : DrawerRoute("beneficiarios")
    object Candidaturas : DrawerRoute("candidaturas")
    object Doacoes : DrawerRoute("donations")
    object Produtos : DrawerRoute("stock")
    object Relatorios : DrawerRoute("relatorios")
}
