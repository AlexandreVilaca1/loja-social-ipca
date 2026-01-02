package com.example.sas.presentation.ui.Apontamentos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sas.domain.common.dummyAgendamentos
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.TextDark

@Composable
fun AgendamentosView(navController: NavController) {
    val agendamentos = dummyAgendamentos()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
            .padding(16.dp)
    ) {

        TotalAgendamentosCard(
            total = agendamentos.size,
            onClick = {
                navController.navigate("agendamentos/todos")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Próximos Agendamentos",
            style = MaterialTheme.typography.titleMedium,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        agendamentos.take(3).forEach {
            AgendamentoCard(it)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
