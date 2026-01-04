package com.example.sas.presentation.ui.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sas.domain.models.Distribution
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary
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

    var showStatusDialog by remember { mutableStateOf(false) }
    var selectedDistribution by remember { mutableStateOf<Distribution?>(null) }

    // Refresh data whenever this screen becomes visible
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

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
                    AppointmentCard(distribution) {
                        selectedDistribution = distribution
                        showStatusDialog = true
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    // Dialog para atualizar status
    if (showStatusDialog && selectedDistribution != null) {
        UpdateStatusDialog(
            distribution = selectedDistribution!!,
            onDismiss = {
                showStatusDialog = false
                selectedDistribution = null
            },
            onDelivered = {
                viewModel.markAsDelivered(selectedDistribution!!.id)
                showStatusDialog = false
                selectedDistribution = null
            },
            onNoShow = {
                viewModel.markAsNoShow(selectedDistribution!!.id)
                showStatusDialog = false
                selectedDistribution = null
            }
        )
    }
}

@Composable
private fun UpdateStatusDialog(
    distribution: Distribution,
    onDismiss: () -> Unit,
    onDelivered: () -> Unit,
    onNoShow: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Atualizar Status da Distribuição",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                Text(
                    text = "Data: ${distribution.distributionDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Selecione o novo status:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Botão: Entrega Realizada
                Button(
                    onClick = onDelivered,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {
                    Text("✓ Entrega Realizada")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botão: Não Compareceu
                Button(
                    onClick = onNoShow,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("✗ Não Compareceu")
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}