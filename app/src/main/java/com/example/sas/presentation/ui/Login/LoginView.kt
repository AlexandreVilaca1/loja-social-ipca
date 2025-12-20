package com.example.sas.presentation.ui.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sas.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navController: NavController
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val green = Color(0xFF2FBF71)
    val lightBg = Color(0xFFF6FFFA)

    val viewModel : LoginViewModel = hiltViewModel()
    val uiState = viewModel.uiState.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = lightBg
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_logo_sas),
                        contentDescription = null,
                        modifier = Modifier
                            .height(180.dp)
                            .padding(top = 8.dp),
                        contentScale = ContentScale.Fit
                    )


                    // 🧾 FORMULÁRIO
                    Text(
                        text = "Bem-vindo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Entra para continuar",
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    OutlinedTextField(
                        value = uiState.email?:"",
                        onValueChange = {viewModel.setEmail(it)},
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.password ?: "",
                        onValueChange = { viewModel.setPassword(it) },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { passwordVisible = !passwordVisible }
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Default.VisibilityOff
                                    else
                                        Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = false,
                                onCheckedChange = {}
                            )
                            Text(
                                text = "Lembra-me",
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Esqueceste da password?",
                            color = green,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable {}
                        )

                    }

                    Spacer(modifier = Modifier.height(60.dp))

                    Button(
                        onClick = {
                            viewModel.login { navController.navigate("home") }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = green)
                    ) {
                        Text("Entrar", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // pequeno respiro inferior
                    Spacer(modifier = Modifier.weight(0.3f))
                }
            }
        }
    }
}
