package com.example.sas.presentation.ui.candidacies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sas.domain.models.Candidacy
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidaciesView(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: CandidaciesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Text(
            text = "Gestão de Candidaturas",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros Rápidos
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = state.selectedFilter == "PENDENTE",
                    onClick = { viewModel.onFilterChanged("PENDENTE") },
                    label = { Text("Pendentes") },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = GreenPrimary.copy(0.2f), selectedLabelColor = GreenPrimary)
                )
            }
            item {
                FilterChip(
                    selected = state.selectedFilter == null,
                    onClick = { viewModel.onFilterChanged(null) },
                    label = { Text("Todas") }
                )
            }
            item {
                FilterChip(
                    selected = state.selectedFilter == "APROVADO",
                    onClick = { viewModel.onFilterChanged("APROVADO") },
                    label = { Text("Aprovadas") }
                )
            }
            item {
                FilterChip(
                    selected = state.selectedFilter == "REJEITADO",
                    onClick = { viewModel.onFilterChanged("REJEITADO") },
                    label = { Text("Rejeitadas") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFDC3545).copy(0.2f),
                        selectedLabelColor = Color(0xFFDC3545)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.error != null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Erro: ${state.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = GreenPrimary)
            }
        } else if (state.candidacies.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma candidatura encontrada.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.candidacies) { candidacy ->
                    CandidacyCard(
                        candidacy = candidacy,
                        onApprove = { viewModel.approveCandidacy(candidacy.id) },
                        onReject = { viewModel.rejectCandidacy(candidacy.id) },
                        isProcessing = state.isProcessing
                    )
                }
            }
        }
    }
}

@Composable
private fun CandidacyCard(
    candidacy: Candidacy,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    isProcessing: Boolean
) {
    // Extrair o nome das observações (assumindo o formato salvo anteriormente)
    val candidateName = candidacy.observations
        ?.lines()
        ?.find { it.startsWith("Nome:") }
        ?.substringAfter("Nome:")?.trim() ?: "Candidato Desconhecido"

    val details = parseCandidacyDetails(candidacy.observations)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = GreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = candidateName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Status Badge
                val statusColor = when(candidacy.status?.code) {
                    "APROVADO" -> GreenPrimary
                    "PENDENTE" -> Color(0xFFFFC107)
                    else -> Color(0xFFDC3545)
                }

                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = candidacy.status?.description ?: "Pendente",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.CalendarMonth, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Submetido em: ${candidacy.applicationDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Dados do Beneficiário", style = MaterialTheme.typography.labelSmall, color = GreenPrimary, fontWeight = FontWeight.Bold)
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), thickness = 0.5.dp, color = Color.LightGray)

            InfoRow("NIF", details["NIF"])
            InfoRow("Nº Aluno", details["Nº Aluno"])
            InfoRow("Curso", details["Curso"])
            InfoRow("Contacto", details["Contacto"])

            if (!details["Motivo"].isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Motivação:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Surface(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = details["Motivo"] ?: "",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                }
            }

            // Botões de Ação (Apenas para pendentes)
            if (candidacy.status?.code == "PENDENTE") {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        enabled = !isProcessing,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC3545)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Close, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Rejeitar")
                    }

                    Button(
                        onClick = onApprove,
                        modifier = Modifier.weight(1f),
                        enabled = !isProcessing,
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (isProcessing) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Aprovar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            Text(text = value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
        }
    }
}

private fun parseCandidacyDetails(observations: String?): Map<String, String> {
    if (observations == null) return emptyMap()

    val details = mutableMapOf<String, String>()
    val lines = observations.lines()

    // Lista de campos que queremos extrair
    val keys = listOf("Nome", "Nº Aluno", "NIF", "Curso", "Contacto", "Motivo")

    lines.forEach { line ->
        keys.forEach { key ->
            if (line.startsWith("$key:")) {
                details[key] = line.substringAfter("$key:").trim()
            }
        }
    }

    // Caso o motivo esteja num bloco separado em baixo de "[MOTIVAÇÃO]"
    if (observations.contains("[MOTIVAÇÃO]")) {
        details["Motivo"] = observations.substringAfter("[MOTIVAÇÃO]").trim()
    }

    return details
}