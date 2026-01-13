package com.example.sas.presentation.ui.distributionItems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistributionItemsView(
    viewModel: DistributionItemsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var showAddItemSheet by remember { mutableStateOf(false) }
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
    ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = GreenPrimary)
                }
            } else if (state.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else if (state.items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "Adicione Produtos a esta distribuição",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "${state.items.size} produtos distribuídos",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(state.items) { item ->
                        DistributionItemCard(
                            item = item,
                            canDelete = state.distributionStatusCode == "POR_ENTREGAR",
                            onDeleteClick = { distributionId, lotId ->
                                viewModel.deleteDistributionItem(
                                    distributionId = distributionId,
                                    lotId = lotId,
                                    onSuccess = {}
                                )
                            }
                        )
                    }
                }
            }
            // FAB para adicionar produto - apenas se status for POR_ENTREGAR
            if (!state.isLoading && state.error == null && state.distributionStatusCode == "POR_ENTREGAR") {
                FloatingActionButton(
                    onClick = { showAddItemSheet = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = GreenPrimary,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Produto")
                }
            }
        }

    // ModalBottomSheet para adicionar produto
    if (showAddItemSheet) {
        AddItemBottomSheet(
            sheetState = sheetState,
            state = state,
            viewModel = viewModel,
            onDismiss = {
                showAddItemSheet = false
                viewModel.clearError()
            },
            onSuccess = {
                showAddItemSheet = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddItemBottomSheet(
    sheetState: SheetState,
    state: DistributionItemsUiState,
    viewModel: DistributionItemsViewModel,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var selectedLotId by remember { mutableStateOf("") }
    var selectedLotName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var observations by remember { mutableStateOf("") }
    var showLotDropdown by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            viewModel.clearLotSearch()
            onDismiss()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Adicionar Produto",
                style = MaterialTheme.typography.titleLarge,
                color = GreenPrimary
            )

            // Dropdown para selecionar lote com pesquisa
            ExposedDropdownMenuBox(
                expanded = showLotDropdown,
                onExpandedChange = { showLotDropdown = !showLotDropdown }
            ) {
                OutlinedTextField(
                    value = selectedLotName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecionar Lote") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showLotDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GreenPrimary,
                        focusedLabelColor = GreenPrimary
                    )
                )

                ExposedDropdownMenu(
                    expanded = showLotDropdown,
                    onDismissRequest = {
                        showLotDropdown = false
                        viewModel.clearLotSearch()
                    }
                ) {
                    OutlinedTextField(
                        value = state.lotSearchQuery,
                        onValueChange = { viewModel.setLotSearchQuery(it) },
                        placeholder = { Text("Pesquisar lote...", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GreenPrimary,
                            focusedLabelColor = GreenPrimary
                        )
                    )

                    // Lógica de exibição baseada na pesquisa
                    when {
                        // Campo de pesquisa vazio - mostrar mensagem
                        state.lotSearchQuery.isBlank() -> {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Digite para pesquisar lotes...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                },
                                onClick = { },
                                enabled = false
                            )
                        }
                        // Pesquisa feita mas sem resultados
                        state.availableLots.isEmpty() -> {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Nenhum lote encontrado",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                },
                                onClick = { },
                                enabled = false
                            )
                        }
                        // Mostrar resultados da pesquisa
                        else -> {
                            state.availableLots.forEach { lot ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = lot.lotCode,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = "Quantidade disponível: ${lot.currentQuantity}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.Gray
                                            )
                                        }
                                    },
                                    onClick = {
                                        selectedLotId = lot.id
                                        selectedLotName = lot.lotCode
                                        showLotDropdown = false
                                        viewModel.clearLotSearch()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Campo de Quantidade
            OutlinedTextField(
                value = quantity,
                onValueChange = { newValue ->
                    // Permite campo vazio ou apenas números
                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                        quantity = newValue
                    }
                },
                label = { Text("Quantidade") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    focusedLabelColor = GreenPrimary
                ),
                isError = quantity.isNotEmpty() && (quantity.toIntOrNull() ?: 0) <= 0,
                supportingText = if (quantity.isNotEmpty() && (quantity.toIntOrNull() ?: 0) <= 0) {
                    { Text("A quantidade deve ser maior que 0") }
                } else null
            )

            // Campo de Observações
            OutlinedTextField(
                value = observations,
                onValueChange = { observations = it },
                label = { Text("Observações (opcional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    focusedLabelColor = GreenPrimary
                )
            )

            // Erro
            if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Botão Adicionar
            Button(
                onClick = {
                    val quantityInt = quantity.toIntOrNull() ?: 0
                    if (selectedLotId.isNotBlank() && quantityInt > 0) {
                        viewModel.createDistributionItem(
                            lotId = selectedLotId,
                            quantity = quantityInt,
                            observations = observations.ifBlank { null },
                            onSuccess = onSuccess
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !state.isCreating && selectedLotId.isNotBlank() && (quantity.toIntOrNull() ?: 0) > 0,
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (state.isCreating) "Adicionando..." else "Adicionar Produto",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


