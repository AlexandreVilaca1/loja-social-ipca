package com.example.sas.presentation.ui.stock.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sas.presentation.ui.theme.BackgroundGreen
import com.example.sas.presentation.ui.theme.GreenPrimary

@Composable
fun StockHomeView(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController,
    viewModel: StockHomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGreen)
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Text(
            text = "Gestão de Stock",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = GreenPrimary)
            }
        } else {

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                // Produtos
                SummaryCard(
                    title = "Produtos",
                    icon = Icons.Outlined.ShoppingCart,
                    stats = listOf(
                        StatItem("Total", state.totalProducts.toString(), GreenPrimary),
                        StatItem("Ativos", state.activeProducts.toString(), Color(0xFF4CAF50))
                    ),
                    onClick = { navController.navigate("stock/products") }
                )

                // Lotes
                SummaryCard(
                    title = "Lotes",
                    icon = Icons.Outlined.Inventory,
                    stats = listOf(
                        StatItem("Total", state.totalLots.toString(), GreenPrimary),
                        StatItem("A vencer", state.lotsExpiringSoon.toString(), Color(0xFFFFC107)),
                        StatItem("Vencidos", state.lotsExpired.toString(), Color(0xFFDC3545))
                    ),
                    highlight = state.lotsExpired > 0 || state.lotsExpiringSoon > 0,
                    onClick = { navController.navigate("stock/lots") }
                )

                // Categorias
                SummaryCard(
                    title = "Categorias",
                    icon = Icons.Outlined.Category,
                    stats = listOf(
                        StatItem("Total", state.totalCategories.toString(), GreenPrimary)
                    ),
                    onClick = { navController.navigate("stock/categories") }
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    icon: ImageVector,
    stats: List<StatItem>,
    highlight: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlight) Color(0xFFFFF9E6) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = GreenPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C1C1C)
                    )
                }

                if (highlight) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = "Atenção",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                stats.forEach { stat ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stat.value,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = stat.color
                        )
                        Text(
                            text = stat.label,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

private data class StatItem(
    val label: String,
    val value: String,
    val color: Color
)
