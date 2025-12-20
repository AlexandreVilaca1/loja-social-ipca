package com.example.sas.domain.models

data class Agendamento(
    val id: Int,
    val data: String,
    val hora: String,
    val local: String,
    val tipoAgendamento: Int, // 1 = Entrega | 2 = Recolha
    val observacoes: String?,
    val statusAlteradoEm: String,
    val createdAt: String,
    val updatedAt: String
)


