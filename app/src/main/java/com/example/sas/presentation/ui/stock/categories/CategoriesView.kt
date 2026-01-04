package com.example.sas.presentation.ui.stock.categories


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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
fun CategoriesView(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var showFormSheet by remember { mutableStateOf(false) }
    val sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        viewModel.refreshCategories()
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
            Text(
                text = "Categorias de Produtos",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1C1C1C),
                modifier = Modifier.padding(bottom = 16.dp)
            )

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
                    items(state.categories) { category ->
                        CategoryCard(
                            category = category,
                            onEdit = {
                                viewModel.startEditCategory(category)
                            },
                            onDelete = {
                                viewModel.deleteCategory(category.id)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total: ${state.categories.size} categorias",
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
                    viewModel.cancelEdit()
                    showFormSheet = true
                },
                containerColor = GreenPrimary,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar categoria")
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
            CategoryFormSheet(
                state = state,
                viewModel = viewModel,
                onDismiss = {
                    showFormSheet = false
                    viewModel.cancelEdit()
                }
            )
        }
    }
}

@Composable
private fun CategoryFormSheet(
    state: CategoriesUiState,
    viewModel: CategoriesViewModel,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            if (state.isEditMode) "Editar categoria" else "Adicionar categoria",
            style = MaterialTheme.typography.titleLarge
        )

        if (state.error != null) {
            Text(state.error!!, color = MaterialTheme.colorScheme.error)
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            onValueChange = viewModel::setName,
            label = { Text("Nome *") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.description,
            onValueChange = viewModel::setDescription,
            label = { Text("Descrição") },
            minLines = 2,
            maxLines = 4
        )

        Column {
            Text("Ativa")
            Checkbox(
                checked = state.active,
                onCheckedChange = viewModel::setActive
            )
        }

        androidx.compose.material3.Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            onClick = {
                if (state.isEditMode) {
                    viewModel.updateCategory()
                } else {
                    viewModel.createCategory()
                }
                onDismiss()
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
            onClick = onDismiss
        ) {
            Text("Cancelar")
        }

        Spacer(Modifier.height(8.dp))
    }
}