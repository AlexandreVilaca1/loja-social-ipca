package com.example.sas.presentation.ui.beneficiaries

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Beneficiary
import com.example.sas.domain.usecase.beneficiaries.CreateBeneficiaryUseCase
import com.example.sas.domain.usecase.beneficiaries.ListBeneficiariesUseCase
import com.example.sas.domain.usecase.beneficiaries.SearchBeneficiariesUseCase
import com.example.sas.domain.usecase.beneficiaries.UpdateBeneficiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeneficiariesViewModel @Inject constructor(
    private val listBeneficiariesUseCase: ListBeneficiariesUseCase,
    private val createBeneficiaryUseCase: CreateBeneficiaryUseCase,
    private val updateBeneficiaryUseCase: UpdateBeneficiaryUseCase,
    private val searchBeneficiariesUseCase: SearchBeneficiariesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BeneficiariesUiState())
    val uiState: StateFlow<BeneficiariesUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    private companion object {
        const val TAG = "BeneficiariosVM"
    }

    fun setFullName(value: String) = _uiState.update { it.copy(fullName = value) }
    fun setStudentNumber(value: String) = _uiState.update { it.copy(studentNumber = value) }
    fun setNif(value: String) = _uiState.update { it.copy(nif = value) }
    fun setCourse(value: String) = _uiState.update { it.copy(course = value) }
    fun setIsActive(value: Boolean) = _uiState.update { it.copy(isActive = value) }
    fun setContactNumber(value: String) = _uiState.update { it.copy(contactNumber = value) }
    fun setAddress(value: String) = _uiState.update { it.copy(address = value) }
    fun setObservations(value: String) = _uiState.update { it.copy(observations = value) }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        // Cancel previous search job
        searchJob?.cancel()

        // Debounce search - wait 500ms after user stops typing
        searchJob = viewModelScope.launch {
            delay(500)
            if (query.isBlank()) {
                refreshBeneficiaries()
            } else {
                searchBeneficiaries(query)
            }
        }
    }

    private fun searchBeneficiaries(searchTerm: String) {
        viewModelScope.launch {
            Log.d(TAG, "Searching beneficiaries with term: $searchTerm")

            searchBeneficiariesUseCase.execute(searchTerm = searchTerm, limit = 50, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is ResultWrapper.Success -> {
                        Log.d(TAG, "Search finished. count=${result.data?.size ?: 0}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                beneficiaries = result.data ?: emptyList()
                            )
                        }
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "Search FAILED: ${result.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Erro desconhecido"
                            )
                        }
                    }
                }
            }
        }
    }

    fun refreshBeneficiaries() {
        viewModelScope.launch {
            Log.d(TAG, "Calling listBeneficiaries...")

            listBeneficiariesUseCase.execute(limit = 50, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is ResultWrapper.Success -> {
                        Log.d(TAG, "listBeneficiaries finished. count=${result.data?.size ?: 0}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                beneficiaries = result.data ?: emptyList()
                            )
                        }
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "listBeneficiaries FAILED: ${result.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Erro desconhecido"
                            )
                        }
                    }
                }
            }
        }
    }

    fun createBeneficiary() {
        val state = _uiState.value

        val student = state.studentNumber.toIntOrNull()
        if (state.fullName.isBlank() || student == null || state.nif.isBlank() ||
            state.course.isBlank() || state.contactNumber.isBlank()) {
            _uiState.update {
                it.copy(error = "Preenche os campos obrigatórios (nome, nº aluno, nif, curso, contacto)")
            }
            return
        }

        viewModelScope.launch {
            createBeneficiaryUseCase.execute(
                fullName = state.fullName,
                studentNumber = student,
                nif = state.nif,
                course = state.course,
                isActive = state.isActive,
                contactNumber = state.contactNumber,
                address = state.address.ifBlank { null },
                observations = state.observations.ifBlank { null }
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null, createdBeneficiaryId = null) }
                    }
                    is ResultWrapper.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                createdBeneficiaryId = result.data
                            )
                        }
                        refreshBeneficiaries()
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Erro ao criar beneficiário"
                            )
                        }
                    }
                }
            }
        }
    }

    fun startEditBeneficiary(beneficiary: Beneficiary) {
        _uiState.update {
            it.copy(
                isEditMode = true,
                editingBeneficiaryId = beneficiary.id,
                fullName = beneficiary.fullName,
                studentNumber = beneficiary.studentNumber.toString(),
                nif = beneficiary.nif,
                course = beneficiary.course,
                isActive = beneficiary.isActive,
                contactNumber = beneficiary.contactNumber,
                address = beneficiary.address ?: "",
                observations = beneficiary.observations ?: "",
                error = null
            )
        }
    }

    fun cancelEdit() {
        _uiState.update {
            it.copy(
                isEditMode = false,
                editingBeneficiaryId = null,
                fullName = "",
                studentNumber = "",
                nif = "",
                course = "",
                isActive = true,
                contactNumber = "",
                address = "",
                observations = "",
                error = null
            )
        }
    }

    fun updateBeneficiary() {
        val state = _uiState.value

        if (state.editingBeneficiaryId == null) {
            _uiState.update { it.copy(error = "ID do beneficiário não encontrado") }
            return
        }

        val student = state.studentNumber.toIntOrNull()
        if (state.fullName.isBlank() || student == null || state.nif.isBlank() ||
            state.course.isBlank() || state.contactNumber.isBlank()) {
            _uiState.update {
                it.copy(error = "Preenche os campos obrigatórios (nome, nº aluno, nif, curso, contacto)")
            }
            return
        }

        viewModelScope.launch {
            updateBeneficiaryUseCase.execute(
                id = state.editingBeneficiaryId,
                fullName = state.fullName,
                studentNumber = student,
                nif = state.nif,
                course = state.course,
                isActive = state.isActive,
                contactNumber = state.contactNumber,
                address = state.address.ifBlank { null },
                observations = state.observations.ifBlank { null }
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is ResultWrapper.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isEditMode = false,
                                editingBeneficiaryId = null
                            )
                        }
                        cancelEdit()
                        refreshBeneficiaries()
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Erro ao atualizar beneficiário"
                            )
                        }
                    }
                }
            }
        }
    }
}
