import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.sas.presentation.ui.Drawer.DrawerRoute
import com.example.sas.presentation.ui.theme.GrayInactive
import com.example.sas.presentation.ui.theme.GreenPrimary

data class DrawerItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun DrawerContent(
    currentRoute: String?,
    onDestinationClick: (String) -> Unit
) {
    val items = listOf(
        DrawerItem("Agendamentos", DrawerRoute.Agendamentos.route, Icons.Outlined.Event),
        DrawerItem("Beneficiários", DrawerRoute.Beneficiarios.route, Icons.Outlined.People),
        DrawerItem("Doações", DrawerRoute.Doacoes.route, Icons.Outlined.FavoriteBorder),
        DrawerItem("Produtos", DrawerRoute.Produtos.route, Icons.Outlined.Inventory2),
        DrawerItem("Relatórios", DrawerRoute.Relatorios.route, Icons.Outlined.Description)
    )


    ModalDrawerSheet(
        drawerContainerColor = Color.White
    ) {

        Text(
            text = "Menu",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp),
            color = GreenPrimary
        )

        items.forEach { item ->

            val selected = currentRoute?.startsWith(item.route) == true

            NavigationDrawerItem(
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) GreenPrimary else GrayInactive
                    )
                },
                selected = selected,
                onClick = { onDestinationClick(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) GreenPrimary else GrayInactive
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0x1432A852), // verde com leve alpha
                    unselectedContainerColor = Color.Transparent
                )
            )
        }
    }
}
