package com.example.sas.presentation.ui.stock.lots

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DateRange
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotsView(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    productId: String? = null, // Optional filter by product
    viewModel: LotsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val isSAS = state.userRole == "SAS"

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

            Spacer(modifier = Modifier.height(12.dp))

            // Product filter dropdown
            var expandedProductDropdown by remember { mutableStateOf(false) }
            var productSearchQuery by remember { mutableStateOf("") }

            ExposedDropdownMenuBox(
                expanded = expandedProductDropdown,
                onExpandedChange = { expandedProductDropdown = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.selectedProductForSearch?.name ?: "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    placeholder = { Text("Selecionar produto", color = Color.Gray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProductDropdown) },
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

                ExposedDropdownMenu(
                    expanded = expandedProductDropdown,
                    onDismissRequest = { expandedProductDropdown = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Search field inside dropdown
                    OutlinedTextField(
                        value = productSearchQuery,
                        onValueChange = {
                            productSearchQuery = it
                            viewModel.setProductSearchQuery(it)
                        },
                        placeholder = { Text("Pesquisar produto...", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        singleLine = true
                    )

                    // Clear filter option
                    DropdownMenuItem(
                        text = { Text("Todos os produtos", style = MaterialTheme.typography.bodyMedium) },
                        onClick = {
                            viewModel.selectProductForSearch(null)
                            productSearchQuery = ""
                            expandedProductDropdown = false
                        }
                    )

                    // Filtered products
                    viewModel.filteredProducts.forEach { product ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(product.name, style = MaterialTheme.typography.bodyMedium)
                                    Text(
                                        "Categoria: ${product.categoryName ?: "N/A"}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            },
                            onClick = {
                                viewModel.selectProductForSearch(product)
                                productSearchQuery = ""
                                expandedProductDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Expiration date search with DatePicker (only visible when product is selected)
            if (state.selectedProductForSearch != null) {
                var showDatePicker by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = state.expirationDateSearch,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    enabled = false,
                    placeholder = { Text("Selecionar data de validade", color = Color.Gray) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendário",
                            tint = GreenPrimary,
                            modifier = Modifier.clickable { showDatePicker = true }
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFFE0E0E0),
                        unfocusedIndicatorColor = Color(0xFFE0E0E0),
                        disabledIndicatorColor = Color(0xFFE0E0E0)
                    ),
                    singleLine = true
                )

                // DatePickerDialog with improved UI
                if (showDatePicker) {
                    val currentCalendar = java.util.Calendar.getInstance()

                    val datePickerState = androidx.compose.material3.rememberDatePickerState(
                        initialSelectedDateMillis = currentCalendar.timeInMillis,
                        yearRange = 2026..2036 // Only from 2026 onwards
                    )

                    androidx.compose.material3.DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        val calendar = java.util.Calendar.getInstance()
                                        calendar.timeInMillis = millis
                                        val year = calendar.get(java.util.Calendar.YEAR)
                                        val month = calendar.get(java.util.Calendar.MONTH) + 1
                                        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                                        val formattedDate = "%04d-%02d-%02d".format(year, month, day)
                                        viewModel.setExpirationDateSearch(formattedDate)
                                    }
                                    showDatePicker = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GreenPrimary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancelar", color = Color.Gray)
                            }
                        }
                    ) {
                        androidx.compose.material3.DatePicker(
                            state = datePickerState,
                            title = {
                                Text(
                                    "Selecionar Data de Validade",
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                            headline = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val calendar = java.util.Calendar.getInstance()
                                    calendar.timeInMillis = millis
                                    val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                                    val month = calendar.getDisplayName(
                                        java.util.Calendar.MONTH,
                                        java.util.Calendar.LONG,
                                        java.util.Locale.getDefault()
                                    )
                                    val year = calendar.get(java.util.Calendar.YEAR)

                                    Text(
                                        "$day de $month, $year",
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = GreenPrimary
                                    )
                                }
                            },
                            colors = androidx.compose.material3.DatePickerDefaults.colors(
                                containerColor = Color.White,
                                titleContentColor = GreenPrimary,
                                headlineContentColor = GreenPrimary,
                                weekdayContentColor = GreenPrimary,
                                subheadContentColor = GreenPrimary,
                                yearContentColor = Color(0xFF1C1C1C),
                                currentYearContentColor = GreenPrimary,
                                selectedYearContentColor = Color.White,
                                selectedYearContainerColor = GreenPrimary,
                                dayContentColor = Color(0xFF1C1C1C),
                                selectedDayContentColor = Color.White,
                                selectedDayContainerColor = GreenPrimary,
                                todayContentColor = GreenPrimary,
                                todayDateBorderColor = GreenPrimary,
                                dayInSelectionRangeContentColor = GreenPrimary,
                                dayInSelectionRangeContainerColor = GreenPrimary.copy(alpha = 0.3f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Clear filters button
                TextButton(
                    onClick = { viewModel.clearSearchFilters() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = GreenPrimary
                    )
                ) {
                    Text("Limpar Filtros")
                }
            }

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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.lots) { lot ->
                        LotCard(
                            lot = lot,
                            productName = state.products.find { it.id == lot.productId }?.name,
                            canEdit = isSAS,
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
        if(isSAS){
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

        // Lot Code (auto-generated)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.lotCode,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            label = { Text("Código do lote (gerado automaticamente)") },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledContainerColor = Color(0xFFF5F5F5),
                disabledLabelColor = Color.Gray
            )
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

        // Expiration Date (Optional)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.expirationDate,
            onValueChange = viewModel::setExpirationDate,
            label = { Text("Data de validade (opcional)") },
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