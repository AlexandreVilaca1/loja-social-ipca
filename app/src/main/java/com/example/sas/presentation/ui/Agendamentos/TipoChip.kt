package com.example.sas.presentation.ui.Agendamentos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sas.presentation.ui.theme.GreenPrimary

@Composable
fun TipoChip(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = GreenPrimary.copy(alpha = 0.15f),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = GreenPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
