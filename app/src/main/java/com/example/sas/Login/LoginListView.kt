import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sas.Login.LoginListViewModel
import com.example.sas.Login.LoginUserViewCell
import com.example.sas.User
import com.example.sas.ui.theme.BackgroundLight
import com.example.sas.ui.theme.GreenLight
import com.example.sas.ui.theme.GreenPrimary

val dummyUsers = listOf(
    User(username = "alice123", password = "password1"),
    User(username = "bob_the_builder", password = "builder123"),
    User(username = "charlie99", password = "charliePass"),
    User(username = "diana_k", password = "dianaSecure"),
    User(username = "eric_dev", password = "codeMaster"),
    User(username = "fiona77", password = "fionaPass"),
    User(username = "george_smith", password = "gsPass123"),
    User(username = "hannah_b", password = "hb2024"),
    User(username = "ian_tech", password = "techyIan"),
    User(username = "julia_rose", password = "rosebud")
)

@Composable
fun LoginListView(
    navController: NavController,
    viewModel: LoginListViewModel,
    modifier: Modifier = Modifier,
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(GreenLight, Color.White)
    )

    var userSearch by remember { mutableStateOf("") }
    val uiState by viewModel.loginListViewState

    val filteredUsers = if (userSearch.isNotEmpty()) {
        uiState.users.filter { it.username.contains(userSearch, ignoreCase = true) }
    } else {
        uiState.users
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLight
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(top = 40.dp)
        ) {
            Text(
                text = "Loja Social IPCA",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Selecione o utilizador para iniciar sessão",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = userSearch,
                onValueChange = { userSearch = it },
                placeholder = { Text("Pesquisar utilizador…") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = GreenPrimary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = GreenPrimary,
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = GreenPrimary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredUsers) { user ->
                        LoginUserViewCell(user = user) {
                            navController.navigate("login/${user.username}")
                        }
                    }
                }
            }
        }
    }
}
