package com.example.sas.presentation.ui.distributions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeneficiaryDistributionsView(
    navController: NavController,
    viewModel: DistributionsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var showCreateSheet by remember { mutableStateOf(false) }
    var showEditSheet by remember { mutableStateOf(false) }
    var selectedDistribution by remember { mutableStateOf<com.example.sas.domain.models.Distribution?>(null) }
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Filtros de status
            item {
                StatusFilterChips(
                    selectedStatus = state.selectedStatusFilter,
                    onStatusSelected = { statusCode ->
                        viewModel.filterByStatus(statusCode)
                    }
                )
            }

            // Estados da UI baseados no ViewModel Unificado
            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = GreenPrimary)
                    }
                }
            } else if (state.error != null && !showCreateSheet && !showEditSheet) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                        Text(text = state.error!!, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
                    }
                }
            } else if (state.distributions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "Nenhuma distribuição encontrada",
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
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
                            navController.navigate("distribution/${dist.id}/items?date=${dist.distributionDate}")
                        },
                        onEdit = { dist ->
                            selectedDistribution = dist
                            showEditSheet = true
                        }
                    )
                }
            }
        }

        // Botão Criar
        viewModel.currentBeneficiaryId?.let { beneficiaryId ->
            FloatingActionButton(
                onClick = {
                    viewModel.onBeneficiaryIdChanged(beneficiaryId)
                    showCreateSheet = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = GreenPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Criar", tint = Color.White)
            }
        }
    }

    // BottomSheet de Criação (Usa as funções create do ViewModel unificado)
    if (showCreateSheet) {
        CreateDistributionBottomSheet(
            sheetState = sheetState,
            state = state,
            viewModel = viewModel,
            onDismiss = {
                showCreateSheet = false
                viewModel.clearError()
            },
            onSuccess = { distributionId ->
                showCreateSheet = false
                viewModel.refreshDistributions()
                // Mantém na mesma página - não navega
            }
        )
    }

    // BottomSheet de Edição (Usa as funções update do ViewModel unificado)
    if (showEditSheet && selectedDistribution != null) {
        EditDistributionBottomSheet(
            sheetState = sheetState,
            distribution = selectedDistribution!!,
            state = state,
            viewModel = viewModel,
            onDismiss = {
                showEditSheet = false
                selectedDistribution = null
                viewModel.clearError()
            },
            onSuccess = {
                showEditSheet = false
                selectedDistribution = null
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
        "POR_ENTREGAR" to "Por Entregar",
        "NAO_COMPARECEU" to "Não Compareceu"
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
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
    state: DistributionsUiState,
    viewModel: DistributionsViewModel,
    onDismiss: () -> Unit,
    onSuccess: (String) -> Unit
) {
    var showUserDropdown by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(text = "Nova Distribuição", style = MaterialTheme.typography.titleLarge, color = GreenPrimary)
            }

            if (state.error != null) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                        Text(text = state.error, modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = state.distributionDate,
                    onValueChange = { viewModel.onDistributionDateChanged(it) },
                    label = { Text("Data (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                )
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = showUserDropdown,
                    onExpandedChange = { showUserDropdown = !showUserDropdown }
                ) {
                    OutlinedTextField(
                        value = state.availableUsers.find { it.id == state.selectedResponsibleStaffId }?.name ?: "Selecionar SAS",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Responsável") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )

                    ExposedDropdownMenu(expanded = showUserDropdown, onDismissRequest = { showUserDropdown = false }) {
                        state.availableUsers.forEach { user ->
                            DropdownMenuItem(
                                text = { Text(user.name) },
                                onClick = {
                                    viewModel.onResponsibleStaffSelected(user.id)
                                    showUserDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = state.observations,
                    onValueChange = { viewModel.onObservationsChanged(it) },
                    label = { Text("Observações") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                )
            }

            item {
                Button(
                    onClick = { viewModel.createDistribution { onSuccess(it) } },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    enabled = !state.isCreating,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    if (state.isCreating) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                    else Text("Criar Distribuição")
                }
            }

            item {
                TextButton(modifier = Modifier.fillMaxWidth(), onClick = onDismiss) { Text("Cancelar") }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditDistributionBottomSheet(
    sheetState: SheetState,
    distribution: com.example.sas.domain.models.Distribution,
    state: DistributionsUiState,
    viewModel: DistributionsViewModel,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var showUserDropdown by remember { mutableStateOf(false) }
    var showStatusDropdown by remember { mutableStateOf(false) }

    LaunchedEffect(distribution.id) {
        viewModel.initializeWithDistribution(distribution)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "Editar Distribuição", style = MaterialTheme.typography.titleLarge)
            }

            item {
                OutlinedTextField(
                    value = state.distributionDate,
                    onValueChange = { viewModel.onDistributionDateChanged(it) },
                    label = { Text("Data (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                )
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = showUserDropdown,
                    onExpandedChange = { showUserDropdown = !showUserDropdown }
                ) {
                    OutlinedTextField(
                        value = state.availableUsers.find { it.id == state.selectedResponsibleStaffId }?.name
                            ?: state.responsibleStaffName ?: "Selecionar responsável",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Responsável") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )

                    ExposedDropdownMenu(expanded = showUserDropdown, onDismissRequest = { showUserDropdown = false }) {
                        state.availableUsers.forEach { user ->
                            DropdownMenuItem(
                                text = { Text(user.name) },
                                onClick = {
                                    viewModel.onResponsibleStaffSelected(user.id)
                                    showUserDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = showStatusDropdown,
                    onExpandedChange = { showStatusDropdown = !showStatusDropdown }
                ) {
                    OutlinedTextField(
                        value = state.availableStatuses.find { it.id == state.selectedStatusId }?.description
                            ?: state.statusDescription ?: "Selecionar status",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )

                    ExposedDropdownMenu(expanded = showStatusDropdown, onDismissRequest = { showStatusDropdown = false }) {
                        state.availableStatuses.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.description) },
                                onClick = {
                                    viewModel.onStatusSelected(status.id)
                                    showStatusDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = state.observations,
                    onValueChange = { viewModel.onObservationsChanged(it) },
                    label = { Text("Observações") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                )
            }

            if (state.error != null) {
                item { Text(text = state.error, color = Color.Red, style = MaterialTheme.typography.bodySmall) }
            }

            item {
                Button(
                    onClick = { viewModel.updateDistribution { onSuccess() } },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    enabled = !state.isUpdating,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    if (state.isUpdating) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                    else Text("Salvar Alterações")
                }
            }

            item {
                TextButton(modifier = Modifier.fillMaxWidth(), onClick = onDismiss) { Text("Cancelar") }
            }
        }
    }
}