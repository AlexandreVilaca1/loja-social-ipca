package com.example.sas.presentation.ui.stock.lots

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.TrendingDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sas.domain.models.Lot
import com.example.sas.presentation.ui.theme.GreenPrimary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun LotCard(
    lot: Lot,
    productName: String?,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onUpdateQuantity: () -> Unit
) {
    val isExpiringSoon = isExpiringSoon(lot.expirationDate)
    val isExpired = isExpired(lot.expirationDate)
    val isLowStock = lot.currentQuantity < (lot.initialQuantity * 0.2) // Less than 20%

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFD6F5E3)),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isExpired -> Color(0xFFFFF0F0)
                isExpiringSoon -> Color(0xFFFFF9E6)
                else -> Color.White
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lot.lotCode,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1C1C),
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (productName != null) {
                        Text(
                            text = productName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = GreenPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Status badges
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        if (isExpired) {
                            StatusBadge("VENCIDO", Color(0xFFDC3545))
                        } else if (isExpiringSoon) {
                            StatusBadge("A VENCER", Color(0xFFFFC107))
                        }

                        if (isLowStock) {
                            StatusBadge("ESTOQUE BAIXO", Color(0xFFFF9800))
                        }
                    }
                }

                Row {
                    IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Apagar",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quantities
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Inicial",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = lot.initialQuantity.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C1C1C)
                    )
                }

                Column {
                    Text(
                        text = "Atual",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = lot.currentQuantity.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isLowStock) Color(0xFFFF9800) else GreenPrimary
                    )
                }

                Column {
                    Text(
                        text = "Utilizado",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    val used = lot.initialQuantity - lot.currentQuantity
                    Text(
                        text = "$used (${(used * 100 / lot.initialQuantity)}%)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C1C1C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Entry date
            InfoRow(
                icon = Icons.Outlined.CalendarMonth,
                text = "Entrada: ${formatDateForDisplay(lot.entryDate)}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Expiration date
            InfoRow(
                icon = Icons.Outlined.EventAvailable,
                text = "Validade: ${formatDateForDisplay(lot.expirationDate)}",
                textColor = when {
                    isExpired -> Color(0xFFDC3545)
                    isExpiringSoon -> Color(0xFFFFC107)
                    else -> Color(0xFF1C1C1C)
                }
            )

            // Observations
            if (!lot.observations.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lot.observations,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Quick quantity update button
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = onUpdateQuantity,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.TrendingDown,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = GreenPrimary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Atualizar quantidade", color = GreenPrimary)
            }
        }
    }
}

@Composable
private fun StatusBadge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.2f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    text: String,
    textColor: Color = Color(0xFF1C1C1C)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GreenPrimary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

private fun formatDateForDisplay(dateStr: String): String {
    return try {
        val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        dateStr
    }
}

private fun isExpiringSoon(expirationDate: String): Boolean {
    return try {
        val date = LocalDate.parse(expirationDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val today = LocalDate.now()
        val daysUntilExpiration = ChronoUnit.DAYS.between(today, date)
        daysUntilExpiration in 1..30 // Expiring in 30 days
    } catch (e: Exception) {
        false
    }
}

private fun isExpired(expirationDate: String): Boolean {
    return try {
        val date = LocalDate.parse(expirationDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        date.isBefore(LocalDate.now())
    } catch (e: Exception) {
        false
    }
}