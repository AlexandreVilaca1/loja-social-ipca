package com.example.sas.presentation.ui.distributionItems

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
            .padding(16.dp)
    ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
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
                    modifier = Modifier.fillMaxSize(),
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
                        .padding(16.dp),
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
                        DistributionItemCard(item = item)
                    }
                }
            }

            // FAB para adicionar produto - apenas se status for POR_ENTREGAR
            if (!state.isLoading && state.error == null && state.distributionStatusCode == "POR_ENTREGAR") {
                FloatingActionButton(
                    onClick = { showAddItemSheet = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
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
    var quantity by remember { mutableIntStateOf(1) }
    var observations by remember { mutableStateOf("") }
    var showLotDropdown by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
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
                color = Color(0xFF1C1C1C)
            )

            // Dropdown para selecionar lote
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
                    onDismissRequest = { showLotDropdown = false }
                ) {
                    state.availableLots.forEach { lot ->
                        DropdownMenuItem(
                            text = { Text(lot.lotCode) },
                            onClick = {
                                selectedLotId = lot.id
                                selectedLotName = lot.lotCode
                                showLotDropdown = false
                            }
                        )
                    }
                }
            }

            // Campo de Quantidade
            OutlinedTextField(
                value = quantity.toString(),
                onValueChange = {
                    it.toIntOrNull()?.let { newQuantity ->
                        if (newQuantity > 0) quantity = newQuantity
                    }
                },
                label = { Text("Quantidade") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    focusedLabelColor = GreenPrimary
                )
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
                    if (selectedLotId.isNotBlank()) {
                        viewModel.createDistributionItem(
                            lotId = selectedLotId,
                            quantity = quantity,
                            observations = observations.ifBlank { null },
                            onSuccess = onSuccess
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !state.isCreating && selectedLotId.isNotBlank(),
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

