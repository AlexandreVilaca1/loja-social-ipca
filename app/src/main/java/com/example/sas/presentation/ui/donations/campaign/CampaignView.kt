package com.example.sas.presentation.ui.campaigns.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.sas.domain.models.Campaign
import com.example.sas.domain.models.StatusType
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsView(
    navController: NavController,
    viewModel: CampaignsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    val isSAS = state.userRole == "SAS"

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
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Procurar campanha...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GreenPrimary) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = GreenPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item { FilterChip(selected = state.selectedType == null, onClick = { viewModel.setTypeFilter(null) }, label = { Text("Todos") }) }
                items(state.availableTypes) { type ->
                    FilterChip(selected = state.selectedType == type, onClick = { viewModel.setTypeFilter(type) }, label = { Text(type) })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = GreenPrimary) }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    val list = if (state.selectedType != null) state.campaigns.filter { it.type == state.selectedType } else state.campaigns
                    items(list) { campaign ->
                        CampaignItemCard(
                            campaign = campaign,
                            canEdit = isSAS,
                            onEdit = { viewModel.startEdit(campaign); showSheet = true },
                            onDelete = { viewModel.deleteCampaign(campaign.id, campaign.name) }
                        )
                    }
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            CampaignFormSheet(state = state, viewModel = viewModel, onDismiss = { showSheet = false })
        }
    }
}

@Composable
fun CampaignItemCard(campaign: Campaign, canEdit: Boolean, onEdit: () -> Unit, onDelete: () -> Unit) {
    val statusColor = when(campaign.status?.code) {
        "A_DECORRER" -> GreenPrimary
        "POR_INICIAR" -> Color(0xFFFFC107)
        "FINALIZADA" -> Color.Gray
        else -> Color.Gray
    }

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(campaign.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text(campaign.type, color = GreenPrimary, style = MaterialTheme.typography.bodySmall)
                }
                Surface(color = statusColor.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text(
                        text = campaign.status?.description ?: "N/A",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, null, Modifier.size(16.dp), tint = Color.Gray)
                Spacer(Modifier.width(4.dp))
                Text("${campaign.startDate} - ${campaign.endDate}", style = MaterialTheme.typography.bodySmall)
            }
            if (canEdit) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, null, Modifier.size(20.dp), tint = Color.Gray) }
                    IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null, Modifier.size(20.dp), tint = Color.Red.copy(0.7f)) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignFormSheet(state: CampaignsUiState, viewModel: CampaignsViewModel, onDismiss: () -> Unit) {
    var expandedStatus by remember { mutableStateOf(false) }
    var expandedType by remember { mutableStateOf(false) }

    val allowedCampaignStatuses = listOf("A_DECORRER", "POR_INICIAR", "FINALIZADA")

    val filteredStatusTypes = state.statusTypes.filter { it.code in allowedCampaignStatuses }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth().padding(bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(if(state.isEditMode) "Editar Campanha" else "Nova Campanha", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onEvent(CampaignFormEvent.NameChanged(it)) },
            label = { Text("Nome da Campanha") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = state.startDate, onValueChange = { viewModel.onEvent(CampaignFormEvent.DatesChanged(it, state.endDate)) }, label = { Text("Início (yyyy-mm-dd)") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = state.endDate, onValueChange = { viewModel.onEvent(CampaignFormEvent.DatesChanged(state.startDate, it)) }, label = { Text("Fim (yyyy-mm-dd)") }, modifier = Modifier.weight(1f))
        }

        OutlinedTextField(
            value = state.address,
            onValueChange = { viewModel.onEvent(CampaignFormEvent.AddressChanged(it)) },
            label = { Text("Morada") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(expanded = expandedType, onExpandedChange = { expandedType = !expandedType }) {
            OutlinedTextField(
                value = state.campaignType,
                onValueChange = {}, readOnly = true, label = { Text("Tipo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedType) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expandedType, onDismissRequest = { expandedType = false }) {
                state.availableTypes.forEach { type ->
                    DropdownMenuItem(text = { Text(type) }, onClick = { viewModel.onEvent(CampaignFormEvent.TypeChanged(type)); expandedType = false })
                }
            }
        }

        if (state.isEditMode) {
            ExposedDropdownMenuBox(expanded = expandedStatus, onExpandedChange = { expandedStatus = !expandedStatus }) {
                OutlinedTextField(
                    value = state.statusTypes.find { it.id == state.statusId }?.description ?: "Selecionar Status",
                    onValueChange = {}, readOnly = true, label = { Text("Status Manual") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedStatus, onDismissRequest = { expandedStatus = false }) {
                    filteredStatusTypes.forEach { status ->
                        DropdownMenuItem(text = { Text(status.description) }, onClick = { viewModel.onEvent(CampaignFormEvent.StatusChanged(status.id)); expandedStatus = false })
                    }
                }
            }
        }

        OutlinedTextField(value = state.observations, onValueChange = { viewModel.onEvent(CampaignFormEvent.ObsChanged(it)) }, label = { Text("Observações") }, modifier = Modifier.fillMaxWidth(), minLines = 2)

        Button(
            onClick = { viewModel.onEvent(CampaignFormEvent.Submit); onDismiss() },
            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) { Text("Guardar", color = Color.White) }
    }
}