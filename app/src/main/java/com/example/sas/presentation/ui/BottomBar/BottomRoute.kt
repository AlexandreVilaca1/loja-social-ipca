package com.example.sas.presentation.ui.BottomBar

sealed class BottomRoute(val route: String) {
    object Agendamentos : BottomRoute("agendamentos")
    object Beneficiarios : BottomRoute("beneficiarios")
    object Doacoes : BottomRoute("doacoes")
    object Produtos : BottomRoute("produtos")
    object Relatorios : BottomRoute("relatorios")
}
