package com.example.sas.presentation.ui.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sas.presentation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestsView(
    navController: NavController,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val displayedRequests = remember(state.allRequests, state.onlyPending) {
        if (state.onlyPending) {
            state.allRequests.filter { it.status?.code == "PENDENTE" }
        } else {
            state.allRequests
        }
    }

    Scaffold(
        containerColor = BackgroundGreen,
        floatingActionButton = {
            if (state.userRole == "Beneficiário") {
                FloatingActionButton(
                    onClick = { showBottomSheet = true },
                    containerColor = GreenPrimary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Novo Pedido")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // --- SELETOR DE FILTRO ESTILIZADO (Substitui o Switch) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FilterTabItem(
                    label = "Pendentes",
                    isSelected = state.onlyPending,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.toggleFilter(true) }
                )
                FilterTabItem(
                    label = "Todos",
                    isSelected = !state.onlyPending,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.toggleFilter(false) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (state.onlyPending) "Pedidos a aguardar análise" else "Histórico de pedidos",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GreenPrimary)
                }
            } else if (displayedRequests.isEmpty()) {
                EmptyStateRequests()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(displayedRequests) { request ->
                        RequestItemCard(
                            request = request,
                            userRole = state.userRole, // Passamos a role atual
                            onAccept = { viewModel.approveRequest(request.id) },
                            onReject = { viewModel.rejectRequest(request.id) }
                        )
                    }
                }
            }
        }
    }

    // --- FORMULÁRIO QUE VEM DE BAIXO (Bottom Sheet) ---
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color.LightGray) }
        ) {
            CreateRequestForm(
                onDismiss = { showBottomSheet = false },
                onConfirm = { desc ->
                    viewModel.createNewRequest(desc)
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
fun FilterTabItem(
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(40.dp)
            .padding(2.dp),
        color = if (isSelected) GreenPrimary else Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Gray
            )
        }
    }
}

@Composable
fun RequestItemCard(
    request: com.example.sas.domain.models.Request,
    userRole: String?,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: ID e Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ID: ${request.id.take(8).uppercase()}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary
                    )
                    // Mostrar nome do beneficiário se for SAS
                    if (userRole == "SAS") {
                        Text(
                            text = request.beneficiary.fullName,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                StatusBadge(request.status?.code ?: "PENDENTE", request.status?.description ?: "Pendente")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = request.description ?: "Sem descrição",
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Footer: Data
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = request.requestDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            // --- AÇÕES DO SAS (Aparecem apenas se for SAS e PENDENTE) ---
            if (userRole == "SAS" && request.status?.code == "PENDENTE") {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Botão Rejeitar
                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDC3545)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC3545))
                    ) {
                        Text("Rejeitar", fontWeight = FontWeight.SemiBold)
                    }

                    // Botão Aceitar
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                    ) {
                        Text("Aceitar", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(code: String, label: String) {
    val (bgColor, textColor) = when (code) {
        "PENDENTE" -> Color(0xFFFFF4E5) to Color(0xFFFF9800)
        "APROVADO" -> Color(0xFFE8F5E9) to Color(0xFF4CAF50)
        "REJEITADO" -> Color(0xFFFFEBEE) to Color(0xFFF44336)
        else -> Color(0xFFF5F5F5) to Color(0xFF616161)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = label.uppercase(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun EmptyStateRequests() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Outlined.Assignment,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Não foram encontrados pedidos.", color = Color.Gray)
    }
}

@Composable
fun CreateRequestForm(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 40.dp) // Espaço para o teclado e navegação
    ) {
        Text(
            text = "Novo Pedido de Apoio",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Explique detalhadamente a sua necessidade para que o SAS possa analisar o seu pedido.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5,
            maxLines = 8,
            placeholder = { Text("Ex: Solicito apoio para a compra de livros escolares do 2º semestre...") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenPrimary,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onConfirm(description) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = description.isNotBlank(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text("Submeter Pedido", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        TextButton(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar", color = Color.Gray)
        }
    }
}