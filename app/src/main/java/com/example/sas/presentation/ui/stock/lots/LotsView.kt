package com.example.sas.presentation.ui.stock.lots

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sas.domain.models.Lot
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotsView(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    productId: String? = null, // Optional filter by product
    viewModel: LotsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var showFormSheet by remember { mutableStateOf(false) }
    var showQuantitySheet by remember { mutableStateOf(false) }
    var selectedLotForQuantity by remember { mutableStateOf<Lot?>(null) }
    val sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadLotsByProduct(productId)
        } else {
            viewModel.refreshLots()
        }
    }

    LaunchedEffect(state.isEditMode) {
        if (state.isEditMode) {
            showFormSheet = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGreen)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Pesquisar por código do lote...", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pesquisar",
                        tint = GreenPrimary
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFFE0E0E0),
                    unfocusedIndicatorColor = Color(0xFFE0E0E0)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Error message
            if (state.error != null) {
                Text(
                    state.error!!,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Loading or content
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GreenPrimary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.lots) { lot ->
                        LotCard(
                            lot = lot,
                            productName = state.products.find { it.id == lot.productId }?.name,
                            onEdit = {
                                viewModel.startEditLot(lot)
                            },
                            onDelete = {
                                viewModel.deleteLot(lot.id)
                            },
                            onUpdateQuantity = {
                                selectedLotForQuantity = lot
                                showQuantitySheet = true
                            }
                        )
                    }

                    // Total count footer
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total: ${state.lots.size} lotes",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF1C1C1C),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // FAB
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    viewModel.cancelEdit()
                    if (productId != null) {
                        viewModel.setProductId(productId)
                    }
                    showFormSheet = true
                },
                containerColor = GreenPrimary,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar lote")
            }
        }
    }

    // Form Bottom Sheet
    if (showFormSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showFormSheet = false
                viewModel.cancelEdit()
            },
            sheetState = sheetState
        ) {
            LotFormSheet(
                viewModel = viewModel,
                onDismiss = {
                    showFormSheet = false
                    viewModel.cancelEdit()
                }
            )
        }
    }

    // Quantity Update Sheet
    if (showQuantitySheet && selectedLotForQuantity != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showQuantitySheet = false
                selectedLotForQuantity = null
            },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            QuantityUpdateSheet(
                lot = selectedLotForQuantity!!,
                onUpdate = { newQuantity ->
                    viewModel.updateQuantity(selectedLotForQuantity!!.id, newQuantity)
                    showQuantitySheet = false
                    selectedLotForQuantity = null
                },
                onDismiss = {
                    showQuantitySheet = false
                    selectedLotForQuantity = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LotFormSheet(
    viewModel: LotsViewModel,
    onDismiss: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var expandedProduct by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            if (state.isEditMode) "Editar lote" else "Adicionar lote",
            style = MaterialTheme.typography.titleLarge
        )

        if (state.error != null) {
            Text(state.error!!, color = MaterialTheme.colorScheme.error)
        }

        // Lot Code
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.lotCode,
            onValueChange = viewModel::setLotCode,
            label = { Text("Código do lote *") }
        )

        // Product Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedProduct,
            onExpandedChange = { expandedProduct = !expandedProduct }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = state.products.find { it.id == state.productId }?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Produto") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProduct) }
            )

            ExposedDropdownMenu(
                expanded = expandedProduct,
                onDismissRequest = { expandedProduct = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Sem produto") },
                    onClick = {
                        viewModel.setProductId(null)
                        expandedProduct = false
                    }
                )
                state.products.forEach { product ->
                    DropdownMenuItem(
                        text = { Text("${product.name} (${product.unitOfMeasure})") },
                        onClick = {
                            viewModel.setProductId(product.id)
                            expandedProduct = false
                        }
                    )
                }
            }
        }

        // Initial Quantity
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.initialQuantity,
            onValueChange = viewModel::setInitialQuantity,
            label = { Text("Quantidade inicial *") },
            placeholder = { Text("Ex: 100") }
        )

        // Current Quantity
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.currentQuantity,
            onValueChange = viewModel::setCurrentQuantity,
            label = { Text("Quantidade atual *") },
            placeholder = { Text("Ex: 85") }
        )

        // Entry Date
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.entryDate,
            onValueChange = viewModel::setEntryDate,
            label = { Text("Data de entrada *") },
            placeholder = { Text("yyyy-MM-dd") }
        )

        // Expiration Date
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.expirationDate,
            onValueChange = viewModel::setExpirationDate,
            label = { Text("Data de validade *") },
            placeholder = { Text("yyyy-MM-dd") }
        )

        // Observations
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.observations,
            onValueChange = viewModel::setObservations,
            label = { Text("Observações") },
            minLines = 2,
            maxLines = 4
        )

        // Save button
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            onClick = {
                if (state.isEditMode) {
                    viewModel.updateLot()
                } else {
                    viewModel.createLot()
                }
                onDismiss()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary,
                contentColor = Color.White
            )
        ) {
            Text(if (state.isEditMode) "Atualizar" else "Guardar")
        }

        // Cancel button
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDismiss
        ) {
            Text("Cancelar")
        }

        Spacer(Modifier.height(8.dp))
    }
}

// ============================================================================
// 4. QUANTITY UPDATE SHEET
// ============================================================================
@Composable
private fun QuantityUpdateSheet(
    lot: Lot,
    onUpdate: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var newQuantity by remember { mutableStateOf(lot.currentQuantity.toString()) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Atualizar quantidade",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            "Lote: ${lot.lotCode}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Text(
            "Quantidade atual: ${lot.currentQuantity}",
            style = MaterialTheme.typography.bodyMedium
        )

        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newQuantity,
            onValueChange = {
                newQuantity = it
                error = null
            },
            label = { Text("Nova quantidade *") },
            placeholder = { Text("Ex: 75") }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val qty = newQuantity.toIntOrNull()
                if (qty == null || qty < 0) {
                    error = "Quantidade inválida"
                } else {
                    onUpdate(qty)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenPrimary,
                contentColor = Color.White
            )
        ) {
            Text("Atualizar")
        }

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDismiss
        ) {
            Text("Cancelar")
        }

        Spacer(Modifier.height(8.dp))
    }
}