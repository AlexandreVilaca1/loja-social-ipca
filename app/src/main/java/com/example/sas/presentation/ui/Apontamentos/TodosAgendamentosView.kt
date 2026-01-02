package com.example.sas.presentation.ui.Apontamentos

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sas.domain.common.dummyAgendamentos
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@Composable
fun TodosAgendamentosView() {
    val agendamentos = dummyAgendamentos()

    Scaffold(
        containerColor = BackgroundGreen,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* criar novo */ },
                containerColor = GreenPrimary
            ) {
                Icon(Icons.Outlined.Add, contentDescription = null)
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(agendamentos) {
                AgendamentoCard(it)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
