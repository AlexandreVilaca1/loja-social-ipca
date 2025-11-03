package com.example.sas.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sas.User

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

data class LoginListState(
    val users: List<User> = dummyUsers,
)

class LoginListViewModel: ViewModel() {
    var loginListViewState = mutableStateOf(LoginListState())
        private set


    fun updateUsers(newList: List<User>) {
        loginListViewState.value = loginListViewState.value.copy(
            users = newList
        )
    }
}