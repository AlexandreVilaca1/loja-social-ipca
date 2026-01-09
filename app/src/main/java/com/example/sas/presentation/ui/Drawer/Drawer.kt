import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sas.presentation.ui.Drawer.DrawerRoute
import com.example.sas.presentation.ui.auth.AuthViewModel
import com.example.sas.presentation.ui.theme.GreenPrimary

private data class DrawerNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun DrawerContent(
    currentRoute: String?,
    onDestinationClick: (String) -> Unit,
    onSignOut: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.uiState.collectAsState()

    val menuItems = remember(authState.currentUser?.role) {
        buildList {
            add(DrawerNavItem("Agendamentos", DrawerRoute.Agendamentos.route, Icons.Outlined.Event))
            add(DrawerNavItem("Stock", DrawerRoute.Produtos.route, Icons.Outlined.Inventory2))
            add(DrawerNavItem("Pedidos", DrawerRoute.Pedidos.route, Icons.Outlined.Assignment))
            if (authState.currentUser?.role == "SAS") {
                add(DrawerNavItem("Beneficiários", DrawerRoute.Beneficiarios.route, Icons.Outlined.People))
                add(DrawerNavItem("Candidaturas", DrawerRoute.Candidaturas.route, Icons.Outlined.AssignmentInd))
                add(DrawerNavItem("Doações", DrawerRoute.Doacoes.route, Icons.Outlined.FavoriteBorder))
                add(DrawerNavItem("Relatórios", DrawerRoute.Relatorios.route, Icons.Outlined.Description))
                add(DrawerNavItem("Novo Utilizador", "action_create_user", Icons.Outlined.PersonAdd))
            }
        }
    }

    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
        modifier = Modifier.width(300.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ================= HEADER =================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                GreenPrimary,
                                GreenPrimary.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 32.dp)
            ) {
                if (authState.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(60.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f),
                            border = BorderStroke(2.dp, Color.White)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = authState.currentUser?.name
                                        ?.firstOrNull()
                                        ?.uppercase() ?: "?",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Column {
                            Text(
                                text = authState.currentUser?.name ?: "Utilizador",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Text(
                                text = authState.currentUser?.email.orEmpty(),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ================= SCROLLABLE MENU =================
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                item {
                    Text(
                        text = "MENU",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp
                        ),
                        letterSpacing = 1.5.sp
                    )
                }

                items(menuItems) { item ->
                    val isSelected = currentRoute?.startsWith(item.route) == true

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 15.sp,
                                fontWeight = if (isSelected)
                                    FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        selected = isSelected,
                        onClick = { onDestinationClick(item.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = GreenPrimary.copy(alpha = 0.1f),
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = GreenPrimary,
                            unselectedIconColor = Color(0xFF616161),
                            selectedTextColor = GreenPrimary,
                            unselectedTextColor = Color(0xFF424242)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }

            // ================= FOOTER =================
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = Color(0xFFF0F0F0)
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Terminar Sessão",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDC3545)
                    )
                },
                selected = false,
                onClick = { authViewModel.signOut { onSignOut() } },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.ExitToApp,
                        contentDescription = null,
                        tint = Color(0xFFDC3545)
                    )
                },
                modifier = Modifier.padding(12.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
