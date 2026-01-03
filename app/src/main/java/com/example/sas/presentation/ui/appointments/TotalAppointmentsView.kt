package com.example.sas.presentation.ui.appointments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@Composable
fun TotalAppointmentsView(
    navController: NavController,
    viewModel: AgendamentosViewModel = hiltViewModel()
) {
    val distributionsState by viewModel.distributionsState.collectAsState()

    Scaffold(
        containerColor = BackgroundGreen,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("agendamentos/criar") },
                containerColor = GreenPrimary
            ) {
                Icon(Icons.Outlined.Add, contentDescription = "Criar novo agendamento")
            }
        }
    ) { padding ->
        when (val state = distributionsState) {
            is ResultWrapper.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ResultWrapper.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message ?: "Erro ao carregar agendamentos",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is ResultWrapper.Success -> {
                val distributions = state.data

                if (distributions.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum agendamento encontrado",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .padding(16.dp)
                    ) {
                        items(distributions) { distribution ->
                            AppointmentCard(distribution) {
                                navController.navigate("agendamentos/detalhe/${distribution.id}")
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}
