package com.example.sas.presentation.ui.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.TextDark

@Composable
fun AppointmentsView(
    navController: NavController,
    viewModel: AgendamentosViewModel
) {

    val totalCount by viewModel.totalCount.collectAsState()
    val todayDistributions by viewModel.todayDistributionsState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
            .padding(16.dp)
    ) {
        TotalAppointmentsCard(
            total = totalCount,
            onClick = {
                navController.navigate("agendamentos/todos")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Agendamentos de Hoje",
            style = MaterialTheme.typography.titleMedium,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Erro desconhecido",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            todayDistributions.isEmpty() -> {
                Text(
                    text = "Nenhum agendamento para hoje",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark
                )
            }
            else -> {
                todayDistributions.forEach { distribution ->
                    AppointmentCard(distribution) {}
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}