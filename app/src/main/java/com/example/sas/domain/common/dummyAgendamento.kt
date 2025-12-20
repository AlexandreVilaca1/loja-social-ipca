package com.example.sas.domain.common

import com.example.sas.domain.models.Agendamento

fun dummyAgendamentos(): List<Agendamento> {
    return listOf(
        Agendamento(
            id = 1,
            data = "20/12/2024",
            hora = "09:00",
            local = "Endereço 1",
            tipoAgendamento = 1,
            observacoes = "Entregar produtos 1",
            statusAlteradoEm = "19/12/2024 14:30",
            createdAt = "15/12/2024 10:00",
            updatedAt = "19/12/2024 14:30"
        ),
        Agendamento(
            id = 2,
            data = "21/12/2024",
            hora = "14:30",
            local = "Endereço 2",
            tipoAgendamento = 2,
            observacoes = null,
            statusAlteradoEm = "20/12/2024 09:15",
            createdAt = "16/12/2024 11:20",
            updatedAt = "20/12/2024 09:15"
        ),
        Agendamento(
            id = 3,
            data = "22/12/2024",
            hora = "10:00",
            local = "Endereço 3",
            tipoAgendamento = 1,
            observacoes = "Entregar produtos 2",
            statusAlteradoEm = "21/12/2024 16:00",
            createdAt = "17/12/2024 08:40",
            updatedAt = "21/12/2024 16:00"
        ),
        Agendamento(
            id = 4,
            data = "23/12/2024",
            hora = "15:00",
            local = "Endereço 4",
            tipoAgendamento = 2,
            observacoes = "Recolher cDoação 3",
            statusAlteradoEm = "22/12/2024 12:10",
            createdAt = "18/12/2024 09:55",
            updatedAt = "22/12/2024 12:10"
        )
    )
}

fun tipoAgendamentoLabel(tipo: Int): String =
    when (tipo) {
        1 -> "Entrega"
        2 -> "Recolha"
        else -> "Outro"
    }


