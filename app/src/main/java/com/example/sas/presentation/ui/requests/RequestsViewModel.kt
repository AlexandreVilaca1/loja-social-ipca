package com.example.sas.presentation.ui.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.*
import com.example.sas.domain.usecase.auth.GetCurrentUserUseCase
import com.example.sas.domain.usecase.beneficiaries.GetBeneficiaryByIdUseCase
import com.example.sas.domain.usecase.beneficiaries.GetBeneficiaryByUserIdUseCase
import com.example.sas.domain.usecase.logs.LogActivityUseCase
import com.example.sas.domain.usecase.requests.*
import com.example.sas.domain.usecase.status.GetStatusTypeByCodeUseCase
import com.example.sas.domain.usecase.users.GetUserByUidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserByUidUseCase: GetUserByUidUseCase,
    private val getBeneficiaryByUserIdUseCase: GetBeneficiaryByUserIdUseCase,
    private val createRequestUseCase: CreateRequestUseCase,
    private val listRequestsUseCase: ListRequestsUseCase,
    private val getStatusByCodeUseCase: GetStatusTypeByCodeUseCase,
    private val updateRequestStatusUseCase: UpdateRequestStatusUseCase,
    private val getBeneficiaryByIdUseCase: GetBeneficiaryByIdUseCase,
    private val getRequestByIdUseCase: GetRequestByIdUseCase,
    private val logActivityUseCase: LogActivityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RequestsUiState())
    val uiState: StateFlow<RequestsUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getCurrentUserUseCase.execute().collect { authResult ->
                if (authResult is ResultWrapper.Success) {
                    val uid = authResult.data?.uid ?: return@collect
                    fetchUserAndBeneficiary(uid)
                }
            }
        }
    }

    private suspend fun fetchUserAndBeneficiary(uid: String) {
        getUserByUidUseCase.execute(uid).collect { userResult ->
            if (userResult is ResultWrapper.Success) {
                val role = userResult.data?.role
                _uiState.update { it.copy(userRole = role) }

                if (role == "Beneficiário") {
                    getBeneficiaryByUserIdUseCase.execute(uid).collect { benResult ->
                        if (benResult is ResultWrapper.Success) {
                            _uiState.update { it.copy(beneficiaryId = benResult.data?.id) }
                            refreshRequests()
                        }
                    }
                } else {
                    refreshRequests()
                }
            }
        }
    }

    fun refreshRequests() {
        val currentState = _uiState.value
        viewModelScope.launch {
            listRequestsUseCase.execute(limit = 50, offset = 0).collect { result ->
                if (result is ResultWrapper.Success) {
                    val allData = result.data ?: emptyList()
                    val filteredData = if (currentState.beneficiaryId != null) {
                        allData.filter { it.beneficiary.id == currentState.beneficiaryId }
                    } else allData

                    _uiState.update { it.copy(
                        allRequests = filteredData,
                        isLoading = false
                    ) }
                }
            }
        }
    }

    fun toggleFilter(onlyPending: Boolean) {
        _uiState.update { it.copy(onlyPending = onlyPending) }
    }

    fun approveRequest(requestId: String) {
        updateStatus(requestId, "APROVADO")
    }

    fun rejectRequest(requestId: String) {
        updateStatus(requestId, "REJEITADO")
    }

    private fun updateStatus(requestId: String, statusCode: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // 1. Obter os dados do PEDIDO primeiro.
            // O objeto Request já contém o beneficiário (request.beneficiary.fullName)
            getRequestByIdUseCase.execute(requestId).collect { requestResult ->
                when (requestResult) {
                    is ResultWrapper.Success -> {
                        val request = requestResult.data ?: return@collect
                        val beneficiaryName = request.beneficiary.fullName
                        val requestDate = request.requestDate

                        // 2. Agora que temos os dados para o log, vamos buscar o ID do novo Status
                        getStatusByCodeUseCase.execute(statusCode).collect { statusResult ->
                            if (statusResult is ResultWrapper.Success) {
                                val statusId = statusResult.data?.id ?: return@collect

                                // 3. Atualizar o status na base de dados
                                updateRequestStatusUseCase.execute(requestId, statusId).collect { updateResult ->
                                    when (updateResult) {
                                        is ResultWrapper.Success -> {
                                            val actionLabel = if (statusCode == "APROVADO") "Aceitou" else "Rejeitou"

                                            logActivityUseCase.execute("$actionLabel Pedido: $beneficiaryName ($requestDate)", "REQUEST")

                                            refreshRequests()
                                        }
                                        is ResultWrapper.Error -> {
                                            _uiState.update { it.copy(error = updateResult.message, isLoading = false) }
                                        }
                                        else -> {}
                                    }
                                }
                            }
                        }
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update { it.copy(error = requestResult.message, isLoading = false) }
                    }
                    else -> {}
                }
            }
        }
    }

    fun createNewRequest(description: String) {
        val beneficiaryId = _uiState.value.beneficiaryId ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // 1. Obter o ID do status PENDENTE
            getStatusByCodeUseCase.execute("PENDENTE").collect { statusResult ->
                if (statusResult is ResultWrapper.Success) {
                    val statusId = statusResult.data?.id
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                    val request = CreateRequestRequest(
                        beneficiaryId = beneficiaryId,
                        requestDate = today,
                        description = description,
                        statusId = statusId
                    )

                    createRequestUseCase.execute(request).collect { result ->
                        if (result is ResultWrapper.Success) {
                            refreshRequests()
                        } else if (result is ResultWrapper.Error) {
                            _uiState.update { it.copy(error = result.message, isLoading = false) }
                        }
                    }
                }
            }
        }
    }
}

data class RequestsUiState(
    val allRequests: List<Request> = emptyList(),
    val beneficiaryId: String? = null,
    val userRole: String? = null,
    val isLoading: Boolean = false,
    val onlyPending: Boolean = true, // Default: Pendentes
    val error: String? = null
)