package com.example.sas.presentation.ui.distributions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sas.presentation.ui.distributions.create.CreateDistributionViewModel
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeneficiaryDistributionsView(
    navController: NavController,
    viewModel: DistributionsViewModel = hiltViewModel(),
    createViewModel: CreateDistributionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val createState by createViewModel.uiState.collectAsState()

    var showCreateSheet by remember { mutableStateOf(false) }
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Filtros de status - sempre visíveis
            item {
                StatusFilterChips(
                    selectedStatus = state.selectedStatusFilter,
                    onStatusSelected = { statusCode ->
                        viewModel.filterByStatus(statusCode)
                    }
                )
            }

            // Conteúdo baseado no estado
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = GreenPrimary)
                    }
                }
            } else if (state.error != null) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error!!,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else if (state.distributions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "Nenhuma distribuição encontrada",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                item {
                    Text(
                        text = "${state.distributions.size} distribuições encontradas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(state.distributions) { distribution ->
                    DistributionCard(
                        distribution = distribution,
                        onViewItems = { dist ->
                            navController.navigate(
                                "distribution/${dist.id}/items?date=${dist.distributionDate}"
                            )
                        }
                    )
                }
            }
        }

        // FloatingActionButton para criar nova distribuição
        viewModel.currentBeneficiaryId?.let { beneficiaryId ->
            FloatingActionButton(
                onClick = {
                    createViewModel.onBeneficiaryIdChanged(beneficiaryId)
                    showCreateSheet = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = GreenPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Criar Distribuição",
                    tint = Color.White
                )
            }
        }
    }

    // ModalBottomSheet para criar distribuição
    if (showCreateSheet) {
        CreateDistributionBottomSheet(
            sheetState = sheetState,
            createState = createState,
            createViewModel = createViewModel,
            onDismiss = {
                showCreateSheet = false
                createViewModel.clearError()
            },
            onSuccess = {
                showCreateSheet = false
                viewModel.refreshDistributions()
            }
        )
    }
}

@Composable
private fun StatusFilterChips(
    selectedStatus: String?,
    onStatusSelected: (String?) -> Unit
) {
    val statusOptions = listOf(
        null to "Todos",
        "ENTREGUE" to "Entregue",
        "NAO_ENTREGUE" to "Não Entregue"
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(statusOptions) { (code, label) ->
            FilterChip(
                selected = selectedStatus == code,
                onClick = { onStatusSelected(code) },
                label = { Text(label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GreenPrimary,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.Gray
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateDistributionBottomSheet(
    sheetState: SheetState,
    createState: com.example.sas.presentation.ui.distributions.create.CreateDistributionUiState,
    createViewModel: CreateDistributionViewModel,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var showUserDropdown by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Nova Distribuição",
                    style = MaterialTheme.typography.titleLarge,
                    color = GreenPrimary
                )
            }

            // Mensagem de erro
            if (createState.error != null) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = createState.error!!,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = createState.distributionDate,
                    onValueChange = { createViewModel.onDistributionDateChanged(it) },
                    label = { Text("Data da Distribuição (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("2025-01-03") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GreenPrimary,
                        focusedLabelColor = GreenPrimary
                    )
                )
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = showUserDropdown,
                    onExpandedChange = { showUserDropdown = !showUserDropdown }
                ) {
                    OutlinedTextField(
                        value = createState.availableUsers.find { it.id == createState.selectedResponsibleStaffId }?.name
                            ?: "Selecionar utilizador",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Funcionário Responsável") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GreenPrimary,
                            focusedLabelColor = GreenPrimary
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = showUserDropdown,
                        onDismissRequest = { showUserDropdown = false }
                    ) {
                        if (createState.isLoadingUsers) {
                            DropdownMenuItem(
                                text = { Text("Carregando...") },
                                onClick = {}
                            )
                        } else {
                            createState.availableUsers.forEach { user ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(user.name, style = MaterialTheme.typography.bodyMedium)
                                            Text(user.email, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                        }
                                    },
                                    onClick = {
                                        createViewModel.onResponsibleStaffSelected(user.id)
                                        showUserDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = "Não Entregue",
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    label = { Text("Status") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Color.Gray,
                        disabledLabelColor = Color.Gray,
                        disabledTextColor = Color.Gray
                    )
                )
            }

            // Campo de Observações
            item {
                OutlinedTextField(
                    value = createState.observations,
                    onValueChange = { createViewModel.onObservationsChanged(it) },
                    label = { Text("Observações (opcional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GreenPrimary,
                        focusedLabelColor = GreenPrimary
                    )
                )
            }

            // Botão de Criar
            item {
                Button(
                    onClick = {
                        createViewModel.createDistribution { _ ->
                            onSuccess()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !createState.isCreating,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {
                    if (createState.isCreating) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(8.dp),
                            color = Color.White
                        )
                    } else {
                        Text("Criar Distribuição")
                    }
                }
            }

            // Botão Cancelar
            item {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismiss
                ) {
                    Text("Cancelar")
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

