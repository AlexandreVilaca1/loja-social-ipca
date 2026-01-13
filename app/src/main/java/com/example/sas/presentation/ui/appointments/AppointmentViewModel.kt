package com.example.sas.presentation.ui.appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.usecase.auth.GetCurrentUserUseCase
import com.example.sas.domain.usecase.beneficiaries.GetBeneficiaryByUserIdUseCase
import com.example.sas.domain.usecase.distributions.ListDistributionsByBeneficiaryUseCase
import com.example.sas.domain.usecase.distributions.ListDistributionsByStatusUseCase
import com.example.sas.domain.usecase.distributions.UpdateDistributionStatusUseCase
import com.example.sas.domain.usecase.status.GetStatusTypeByCodeUseCase
import com.example.sas.domain.usecase.users.GetUserByUidUseCase
import com.google.firebase.Firebase
import com.google.firebase.functions.functions
import kotlinx.coroutines.tasks.await
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel for Agendamentos (Appointments/Distributions) screen.
 */
@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val listDistributionsByStatusUseCase: ListDistributionsByStatusUseCase,
    private val listDistributionsByBeneficiaryUseCase: ListDistributionsByBeneficiaryUseCase,
    private val updateDistributionStatusUseCase: UpdateDistributionStatusUseCase,
    private val getStatusByCodeUseCase: GetStatusTypeByCodeUseCase,
    private val getBeneficiaryByUserIdUseCase: GetBeneficiaryByUserIdUseCase,
    private val getUserByUidUseCase: GetUserByUidUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    // State for all distributions
    private val _distributionsState = MutableStateFlow<ResultWrapper<List<Distribution>>>(ResultWrapper.Loading())
    val distributionsState: StateFlow<ResultWrapper<List<Distribution>>> = _distributionsState.asStateFlow()

    // State for today's distributions
    private val _todayDistributionsState = MutableStateFlow<List<Distribution>>(emptyList())
    val todayDistributionsState: StateFlow<List<Distribution>> = _todayDistributionsState.asStateFlow()

    // State for total count
    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    // State for loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole.asStateFlow()

    // Configure Firebase Functions para a região europe-west1
    private val functions = Firebase.functions("europe-west1")

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true

            // 1. Obter o utilizador da sessão atual
            getCurrentUserUseCase.execute().collect { authResult ->
                if (authResult is ResultWrapper.Success) {
                    val uid = authResult.data?.uid
                    if (uid != null) {
                        // 2. Com o UID, buscar o perfil completo para saber a Role
                        fetchUserRoleAndData(uid)
                    } else {
                        _errorMessage.value = "Utilizador não autenticado."
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private suspend fun fetchUserRoleAndData(uid: String) {
        getUserByUidUseCase.execute(uid).collect { userResult ->
            if (userResult is ResultWrapper.Success) {
                val role = userResult.data?.role
                _userRole.value = role

                if (role == "SAS") {
                    // Fluxo SAS: Carrega tudo que está para entregar
                    loadDistributionsByStatus("POR_ENTREGAR")
                } else if (role == "Beneficiário") {
                    // Fluxo Beneficiário: Carrega apenas o dele
                    loadBeneficiaryData(uid)
                }
            } else if (userResult is ResultWrapper.Error) {
                _errorMessage.value = "Erro ao carregar perfil: ${userResult.message}"
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadBeneficiaryData(uid: String) {
        getBeneficiaryByUserIdUseCase.execute(uid).collect { beneficiaryResult ->
            if (beneficiaryResult is ResultWrapper.Success) {
                val beneficiaryId = beneficiaryResult.data?.id
                if (beneficiaryId != null) {
                    // 3. Carregar distribuições específicas do ID de beneficiário encontrado
                    listDistributionsByBeneficiaryUseCase.execute(beneficiaryId, 50, 0).collect { result ->
                        handleResult(result)
                    }
                } else {
                    _errorMessage.value = "Cadastro de beneficiário não encontrado."
                    _isLoading.value = false
                }
            }
        }
    }

    private fun handleResult(result: ResultWrapper<List<Distribution>>) {
        _distributionsState.value = result
        when (result) {
            is ResultWrapper.Success -> {
                val data = result.data ?: emptyList()
                _totalCount.value = data.size
                filterTodayDistributions(data)
                _isLoading.value = false
            }
            is ResultWrapper.Error -> {
                _errorMessage.value = result.message
                _isLoading.value = false
            }
            else -> {}
        }
    }


    /**
     * Loads distributions by specific status.
     * @param statusCode Status code to filter by (e.g., "PENDING", "COMPLETED", "CANCELLED")
     */
    fun loadDistributionsByStatus(statusCode: String) {
        viewModelScope.launch {
            listDistributionsByStatusUseCase.execute(
                statusCode = statusCode,
                limit = 100,
                offset = 0
            ).collect { result ->
                _distributionsState.value = result

                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = null
                    }
                    is ResultWrapper.Success -> {
                        _isLoading.value = false
                        _totalCount.value = result.data!!.size
                        filterTodayDistributions(result.data!!)
                    }
                    is ResultWrapper.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.message
                    }
                }
            }
        }
    }

    /**
     * Filters distributions to show only today's entries.
     */
    private fun filterTodayDistributions(distributions: List<Distribution>) {
        // 1. Obter a data de hoje usando Calendar
        val today = Calendar.getInstance().time

        // 2. Formatar usando SimpleDateFormat
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val todayFormatted = sdf.format(today)

        // 3. Filtrar a lista
        val todayDistributions = distributions.filter { distribution ->
            distribution.distributionDate == todayFormatted
        }

        _todayDistributionsState.value = todayDistributions
    }
}
