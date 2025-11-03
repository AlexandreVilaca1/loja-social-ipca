package com.example.sas

import LoginListView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sas.Login.LoginListViewModel
import com.example.sas.Login.LoginView
import com.example.sas.ui.theme.SASTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SASTheme {
                val LoginListViewModel: LoginListViewModel = viewModel()
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login_user_list",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("login_user_list") {
                            LoginListView(navController, LoginListViewModel)
                        }
                        composable("login/{username}",
                            arguments = listOf(navArgument("username") { type = NavType.StringType }))
                        { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username")
                            LoginView(username = username ?: "") {
                                navController.navigate("home")
                            }
                        }
                        composable("home") {
                            HomeView()
                        }
                    }
                }
            }
        }
    }
}
