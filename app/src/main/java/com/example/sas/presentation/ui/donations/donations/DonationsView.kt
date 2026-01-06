package com.example.sas.presentation.ui.donations.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.example.sas.domain.models.Donation
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationsView(
    navController: NavController,
    viewModel: DonationsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    var showFilters by remember { mutableStateOf(false) }
    val isSAS = state.userRole == "SAS"

    // Filtragem local combinada (Nome + Data)
    val filteredDonations = remember(state.donations, state.searchQuery, state.filterDate) {
        state.donations.filter { donation ->
            // Filtro de Nome
            val matchesName = donation.donorName.contains(state.searchQuery, ignoreCase = true)

            // Filtro de Data
            val matchesDate = if (state.filterDate.isNotEmpty()) {
                try {
                    val parts = donation.donationDate.split("/")
                    val formatted = "${parts[2]}-${parts[1]}-${parts[0]}"
                    formatted == state.filterDate
                } catch (e: Exception) { false }
            } else true

            matchesName && matchesDate
        }
    }

    Scaffold(
        floatingActionButton = {
            if (isSAS) {
                FloatingActionButton(onClick = { viewModel.clearForm(); showSheet = true }, containerColor = GreenPrimary) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(BackgroundGreen).padding(padding).padding(16.dp)) {
            // Header com Busca e Botão de Filtro
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Procurar doador...") },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = GreenPrimary) },
                    trailingIcon = {
                        if (state.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(Icons.Default.Close, null)
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
                IconButton(onClick = { showFilters = !showFilters }) {
                    Icon(Icons.Default.FilterList, null, tint = if(showFilters) GreenPrimary else Color.Gray)
                }
            }

            // Área de Filtros Expansível
            if (showFilters) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Filtros Avançados", style = MaterialTheme.typography.labelLarge)

                        // Filtro por Data
                        OutlinedTextField(
                            value = state.filterDate,
                            onValueChange = { viewModel.setFilterDate(it) },
                            label = { Text("Data (yyyy-mm-dd)") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { if(state.filterDate.isNotEmpty()) IconButton(onClick = { viewModel.setFilterDate("") }) { Icon(Icons.Default.Close, null) } }
                        )

                        // Filtro por Campanha (Dropdown)
                        var exp by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(expanded = exp, onExpandedChange = { exp = !exp }) {
                            OutlinedTextField(
                                value = state.campaigns.find { it.id == state.filterCampaignId }?.name ?: "Todas as Campanhas",
                                onValueChange = {}, readOnly = true,
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                label = { Text("Campanha") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = exp) }
                            )
                            ExposedDropdownMenu(expanded = exp, onDismissRequest = { exp = false }) {
                                DropdownMenuItem(text = { Text("Todas") }, onClick = { viewModel.setFilterCampaign(null); exp = false })
                                state.campaigns.forEach { c ->
                                    DropdownMenuItem(text = { Text(c.name) }, onClick = { viewModel.setFilterCampaign(c.id); exp = false })
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator(color = GreenPrimary) }
            } else if (filteredDonations.isEmpty()) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Nenhuma doação encontrada.", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredDonations) { donation ->
                        DonationItemCard(
                            navController = navController,
                            donation = donation,
                            canEdit = isSAS,
                            onEdit = { viewModel.startEdit(donation); showSheet = true },
                            onDelete = { viewModel.deleteDonation(donation.id) }
                        )
                    }
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false }, sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)) {
            DonationFormSheet(state = state, viewModel = viewModel, onDismiss = { showSheet = false })
        }
    }
}

@Composable
fun DonationItemCard(navController: NavController,donation: Donation, canEdit: Boolean, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = { navController.navigate("donation/items/${donation.id}") }),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(donation.donorName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("Valor Estimado: ${donation.estimatedValue}€", color = GreenPrimary, style = MaterialTheme.typography.bodySmall)
                }
                Surface(color = GreenPrimary.copy(0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text("Recebida", Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = GreenPrimary, style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, null, Modifier.size(14.dp), tint = Color.Gray)
                Spacer(Modifier.width(4.dp))
                Text(donation.donationDate, style = MaterialTheme.typography.bodySmall)
                if (!donation.campaignName.isNullOrEmpty()) {
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Default.Campaign, null, Modifier.size(14.dp), tint = Color.Gray)
                    Spacer(Modifier.width(4.dp))
                    Text(donation.campaignName, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
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
fun DonationFormSheet(state: DonationsUiState, viewModel: DonationsViewModel, onDismiss: () -> Unit) {
    var expC by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp).fillMaxWidth().padding(bottom = 32.dp), Arrangement.spacedBy(12.dp)) {
        Text(if(state.isEditMode) "Editar Doação" else "Nova Doação", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(value = state.donorName, onValueChange = { viewModel.updateFormField("name", it) }, label = { Text("Nome do Doador *") }, modifier = Modifier.fillMaxWidth())

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = state.donationDate, onValueChange = { viewModel.updateFormField("date", it) }, label = { Text("Data (yyyy-mm-dd) *") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = state.estimatedValue, onValueChange = { viewModel.updateFormField("value", it) }, label = { Text("Valor Est. (€)") }, modifier = Modifier.weight(1f))
        }

        OutlinedTextField(value = state.donorContact, onValueChange = { viewModel.updateFormField("contact", it) }, label = { Text("Contacto") }, modifier = Modifier.fillMaxWidth())

        // Dropdown Campanha
        ExposedDropdownMenuBox(expanded = expC, onExpandedChange = { expC = !expC }) {
            OutlinedTextField(
                value = state.campaigns.find { it.id == state.selectedCampaignId }?.name ?: "Vincular a Campanha (Opcional)",
                onValueChange = {}, readOnly = true, label = { Text("Campanha") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expC) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expC, onDismissRequest = { expC = false }) {
                DropdownMenuItem(text = { Text("Nenhuma") }, onClick = { viewModel.updateFormField("campaign", ""); expC = false })
                state.campaigns.forEach { c ->
                    DropdownMenuItem(text = { Text(c.name) }, onClick = { viewModel.updateFormField("campaign", c.id); expC = false })
                }
            }
        }


        OutlinedTextField(value = state.observations, onValueChange = { viewModel.updateFormField("obs", it) }, label = { Text("Observações") }, modifier = Modifier.fillMaxWidth(), minLines = 2)

        Button(
            onClick = { if(state.isEditMode) viewModel.updateDonation() else viewModel.createDonation(); onDismiss() },
            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) { Text("Guardar Doação", color = Color.White) }
    }
}