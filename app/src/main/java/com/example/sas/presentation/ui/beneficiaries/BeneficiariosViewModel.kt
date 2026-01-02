package com.example.sas.presentation.ui.beneficiaries

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.beneficiaries.CreateBeneficiaryUseCase
import com.example.sas.domain.usecase.beneficiaries.ListBeneficiariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeneficiariosViewModel @Inject constructor(
    private val listBeneficiariesUseCase: ListBeneficiariesUseCase,
    private val createBeneficiaryUseCase: CreateBeneficiaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BeneficiariesUiState())
    val uiState: StateFlow<BeneficiariesUiState> = _uiState.asStateFlow()

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
}
