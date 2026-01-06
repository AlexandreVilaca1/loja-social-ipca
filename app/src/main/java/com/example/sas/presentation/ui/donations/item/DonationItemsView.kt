package com.example.sas.presentation.ui.donations.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sas.domain.models.DonationItem
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationItemsView(
    viewModel: DonationItemsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    val isSAS = state.userRole == "SAS"

    Scaffold(
        floatingActionButton = {
            if (isSAS) {
                FloatingActionButton(onClick = { viewModel.clearForm(); showSheet = true }, containerColor = GreenPrimary) {
                    Icon(Icons.Default.Add, null, tint = Color.White)
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(BackgroundGreen).padding(padding).padding(16.dp)) {
            Text("Itens da Doação", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator(color = GreenPrimary) }
            } else if (state.items.isEmpty()) {
                Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Nenhum item adicionado.", color = Color.Gray) }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(state.items) { item ->
                        // Encontrar o nome do produto na lista de produtos carregada
                        val product = state.products.find { it.id == item.productId }
                        DonationItemCard(
                            item = item,
                            productName = product?.name ?: "Produto Desconhecido",
                            unit = product?.unitOfMeasure ?: "",
                            canEdit = isSAS,
                            onEdit = { viewModel.startEdit(item); showSheet = true },
                            onDelete = { viewModel.deleteItem(item.productId) }
                        )
                    }
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false; viewModel.clearForm() }) {
            DonationItemFormSheet(state = state, viewModel = viewModel, onDismiss = { showSheet = false })
        }
    }
}

@Composable
fun DonationItemCard(item: DonationItem, productName: String, unit: String, canEdit: Boolean, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(productName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("${item.quantity} $unit", color = GreenPrimary, fontWeight = FontWeight.Medium)
                }
            }
            if (!item.observations.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(item.observations, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            if (canEdit) {
                Row(Modifier.fillMaxWidth(), Arrangement.End) {
                    IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, null, Modifier.size(20.dp), tint = Color.Gray) }
                    IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null, Modifier.size(20.dp), tint = Color.Red.copy(0.7f)) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationItemFormSheet(state: DonationItemsUiState, viewModel: DonationItemsViewModel, onDismiss: () -> Unit) {
    var expP by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp).fillMaxWidth().padding(bottom = 32.dp), Arrangement.spacedBy(12.dp)) {
        Text(if(state.isEditMode) "Editar Item" else "Adicionar Item", style = MaterialTheme.typography.headlineSmall)

        // Seleção de Produto (Apenas na criação, ou apenas leitura na edição se preferir)
        ExposedDropdownMenuBox(expanded = expP, onExpandedChange = { if(!state.isEditMode) expP = !expP }) {
            OutlinedTextField(
                value = state.products.find { it.id == state.selectedProductId }?.name ?: "Selecionar Produto *",
                onValueChange = {}, readOnly = true, label = { Text("Produto") },
                trailingIcon = { if(!state.isEditMode) ExposedDropdownMenuDefaults.TrailingIcon(expanded = expP) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                enabled = !state.isEditMode // Geralmente não se muda o produto de um item, apaga-se e cria-se novo
            )
            ExposedDropdownMenu(expanded = expP, onDismissRequest = { expP = false }) {
                state.products.forEach { p ->
                    DropdownMenuItem(text = { Text(p.name) }, onClick = { viewModel.updateFormField("product", p.id); expP = false })
                }
            }
        }

        OutlinedTextField(
            value = state.quantity,
            onValueChange = { viewModel.updateFormField("quantity", it) },
            label = { Text("Quantidade *") },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = state.observations,
            onValueChange = { viewModel.updateFormField("obs", it) },
            label = { Text("Observações") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        Button(
            onClick = { viewModel.saveItem(); onDismiss() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) { Text("Guardar Item", color = Color.White) }
    }
}