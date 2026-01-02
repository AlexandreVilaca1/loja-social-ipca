package com.example.sas.presentation.ui.beneficiaries

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sas.domain.models.Beneficiary
import com.example.sas.presentation.ui.theme.GreenPrimary

@Composable
fun BeneficiaryCard(
    beneficiary: Beneficiary,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFD6F5E3)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: name + action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = beneficiary.fullName,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1C1C1C), // TextDark
                    modifier = Modifier.weight(1f)
                )

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

            Spacer(modifier = Modifier.height(12.dp))

            // Student number
            InfoRow(
                icon = Icons.Outlined.AccountCircle,
                text = beneficiary.studentNumber.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Course
            InfoRow(
                icon = Icons.Outlined.School,
                text = beneficiary.course,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone
            InfoRow(
                icon = Icons.Outlined.Phone,
                text = beneficiary.contactNumber
            )

            Spacer(modifier = Modifier.height(8.dp))

            // NIF
            InfoRow(
                icon = Icons.Outlined.Email,
                text = beneficiary.nif
            )

            // Address (if available)
            if (!beneficiary.address.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(
                    icon = Icons.Outlined.LocationOn,
                    text = beneficiary.address
                )
            }
        }
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

