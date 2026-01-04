package com.example.sas.presentation.ui.beneficiaries

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.beneficiaries) { b ->
                        BeneficiaryCard(
                            beneficiary = b,
                            onEdit = {
                                viewModel.startEditBeneficiary(b)
                            },
                            onDelete = { /* TODO */ },
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
                            text = "Total: ${state.beneficiaries.size} beneficiários",
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
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

