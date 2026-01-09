package com.example.sas.presentation.ui.beneficiaries

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeneficiariesView(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    viewModel: BeneficiariesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var showFormSheet by remember { mutableStateOf(false) }
    val sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        viewModel.refreshBeneficiaries()
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
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Pesquisar por nome ou NIF...", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pesquisar",
                        tint = GreenPrimary
                    )
                },
                shape = RoundedCornerShape(12.dp),
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

            // FilterChips para Ativos/Inativos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = state.activeFilter == ActiveFilter.ACTIVE,
                    onClick = { viewModel.setActiveFilter(ActiveFilter.ACTIVE) },
                    label = { Text("Ativos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GreenPrimary.copy(alpha = 0.2f),
                        selectedLabelColor = GreenPrimary)
                    )
                FilterChip(
                    selected = state.activeFilter == ActiveFilter.INACTIVE,
                    onClick = { viewModel.setActiveFilter(ActiveFilter.INACTIVE) },
                    label = { Text("Inativos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GreenPrimary.copy(alpha = 0.2f),
                        selectedLabelColor = GreenPrimary)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.error != null) {
                Text(
                    state.error!!,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GreenPrimary)
                }
            } else {
                // Aplicar filtro baseado no activeFilter
                val filteredList = when (state.activeFilter) {
                    ActiveFilter.ACTIVE -> state.beneficiaries.filter { it.isActive }
                    ActiveFilter.INACTIVE -> state.beneficiaries.filter { !it.isActive }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredList) { b ->
                        BeneficiaryCard(
                            beneficiary = b,
                            onEdit = {
                                viewModel.startEditBeneficiary(b)
                            },
                            onClick = {
                                navController.navigate(
                                    "beneficiary/${b.id}/distributions?name=${b.fullName}"
                                )
                            }
                        )
                    }

                    // Total count footer
                    item {
                        Text(
                            text = "Total: ${filteredList.size} de ${state.beneficiaries.size} beneficiários",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF1C1C1C),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    viewModel.cancelEdit() // Reset form
                    showFormSheet = true
                },
                containerColor = GreenPrimary,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar beneficiário")
            }
        }
    }

    if (showFormSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showFormSheet = false
                viewModel.cancelEdit()
            },
            sheetState = sheetState
        ) {

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    if (state.isEditMode) "Editar beneficiário" else "Adicionar beneficiário",
                    style = MaterialTheme.typography.titleLarge
                )

                if (state.createdBeneficiaryId != null) {
                    Text("Criado: ${state.createdBeneficiaryId}")
                }

                if (state.error != null) {
                    Text(state.error!!, color = MaterialTheme.colorScheme.error)
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.fullName,
                    onValueChange = viewModel::setFullName,
                    label = { Text("Nome completo *") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.studentNumber,
                    onValueChange = viewModel::setStudentNumber,
                    label = { Text("Nº aluno *") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.nif,
                    onValueChange = viewModel::setNif,
                    label = { Text("NIF *") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.course,
                    onValueChange = viewModel::setCourse,
                    label = { Text("Curso *") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.contactNumber,
                    onValueChange = viewModel::setContactNumber,
                    label = { Text("Contacto *") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.address,
                    onValueChange = viewModel::setAddress,
                    label = { Text("Morada") }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.observations,
                    onValueChange = viewModel::setObservations,
                    label = { Text("Observações") }
                )

                Column {
                    Text("Ativo")
                    Checkbox(
                        checked = state.isActive,
                        onCheckedChange = viewModel::setIsActive
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text("Conectar com Conta", style = MaterialTheme.typography.titleSmall, color = GreenPrimary)

                if (state.selectedUserUid != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = GreenPrimary.copy(alpha = 0.1f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Outlined.AccountCircle, null, tint = GreenPrimary)
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("UID: ${state.selectedUserUid?.take(12)}...", fontWeight = FontWeight.Bold)
                                Text("Conta vinculada", style = MaterialTheme.typography.labelSmall)
                            }
                            IconButton(onClick = { viewModel.setSelectedUserUid(null) }) {
                                Icon(Icons.Default.Close, "Remover", tint = Color.Red)
                            }
                        }
                    }
                } else {
                    // ESTADO: PESQUISA DE CONTA
                    OutlinedTextField(
                        value = state.userSearchQuery,
                        onValueChange = {
                            viewModel.setUserSearchQuery(it)
                            // Continua carregando os usuários em segundo plano para o filtro local funcionar
                            if (state.availableUsers.isEmpty()) viewModel.loadAvailableUsers()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Pesquisar utilizador por nome...") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        trailingIcon = {
                            if (state.userSearchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.setUserSearchQuery("") }) {
                                    Icon(Icons.Default.Clear, null)
                                }
                            }
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent)
                    )

                    // BOX DE RESULTADOS - ALTERADO: Agora só aparece se houver texto digitado
                    if (state.userSearchQuery.isNotBlank()) { // <--- Mudança aqui
                        val results = viewModel.filteredUsers

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                                .background(Color(0xFFF8F8F8), RoundedCornerShape(8.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        ) {
                            if (results.isEmpty()) {
                                Text(
                                    "Nenhum utilizador encontrado.",
                                    modifier = Modifier.padding(16.dp).align(Alignment.Center),
                                    color = Color.Gray
                                )
                            } else {
                                LazyColumn {
                                    items(results) { user ->
                                        ListItem(
                                            modifier = Modifier.clickable {
                                                viewModel.setSelectedUserUid(user.uid)
                                                viewModel.setUserSearchQuery("")
                                            },
                                            headlineContent = { Text(user.name) },
                                            supportingContent = { Text(user.email) },
                                            leadingContent = { Icon(Icons.Outlined.Person, null, tint = GreenPrimary) },
                                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                                        )
                                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                                    }
                                }
                            }
                        }
                    }
                }

                androidx.compose.material3.Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    onClick = {
                        if (state.isEditMode) {
                            viewModel.updateBeneficiary()
                        } else {
                            viewModel.createBeneficiary()
                        }
                        showFormSheet = false
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(if (state.isEditMode) "Atualizar" else "Guardar")
                }

                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        showFormSheet = false
                        viewModel.cancelEdit()
                    }
                ) {
                    Text("Cancelar")
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

