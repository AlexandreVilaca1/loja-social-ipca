package com.example.sas.presentation.ui.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    viewModel: AppointmentViewModel
) {

    val totalCount by viewModel.totalCount.collectAsState()
    val todayDistributions by viewModel.todayDistributionsState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    // Refresh data whenever this screen becomes visible
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TotalAppointmentsCard(
                total = totalCount,
                onClick = {
                    navController.navigate("agendamentos/todos")
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Text(
                text = if (userRole == "SAS") "Agendamentos de Hoje" else "Os Meus Agendamentos de Hoje",
                style = MaterialTheme.typography.titleMedium,
                color = TextDark
            )
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        when {
            isLoading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            errorMessage != null -> {
                item {
                    Text(
                        text = errorMessage ?: "Erro desconhecido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            todayDistributions.isEmpty() -> {
                item {
                    Text(
                        text = "Nenhum agendamento para hoje",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextDark
                    )
                }
            }

            else -> {
                items(todayDistributions) { distribution ->
                    AppointmentCard(distribution) {
                        if (userRole == "SAS") {
                            // SAS navega para detalhes do beneficiário
                            distribution.beneficiaryId?.let { id ->
                                navController.navigate("beneficiary/$id/distributions?name=${distribution.beneficiaryName}")
                            }
                        }
                    }
                }
            }
        }
    }
}