package com.example.sas.presentation.ui.Apontamentos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sas.presentation.ui.theme.CardBorder
import com.example.sas.presentation.ui.theme.GreenPrimary
import com.example.sas.presentation.ui.theme.TextDark

@Composable
fun TotalAgendamentosCard(
    total: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, CardBorder),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Total de Agendamentos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = total.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )
            }

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = GreenPrimary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Event,
                    contentDescription = null,
                    tint = GreenPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}
