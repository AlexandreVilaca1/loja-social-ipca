package com.example.sas.presentation.ui.Drawer

sealed class DrawerRoute(val route: String) {
    object Agendamentos : DrawerRoute("agendamentos")
    object Beneficiarios : DrawerRoute("beneficiarios")
    object Doacoes : DrawerRoute("doacoes")
    object Produtos : DrawerRoute("produtos")
    object Relatorios : DrawerRoute("relatorios")
}
