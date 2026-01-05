package com.example.sas.data.repository

import com.example.sas.data.datasource.UsersDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import com.example.sas.domain.repositories.UsersRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val dataSource: UsersDataSource
) : UsersRepository {

    override fun listUsers(limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listUsers(limit, offset)
            val users = items.map {
                User(
                    id = it.id.toString(),
                    name = it.name,
                    uid = it.uid,
                    email = it.email,
                    role = it.role,
                    createdAt = formatTimestamp(it.createdAt),
                    updatedAt = formatTimestamp(it.updatedAt)
                )
            }
            emit(ResultWrapper.Success(users))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar utilizadores"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserByUid(uid: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getUserByUid(uid)
            val user = item?.let {
                User(
                    id = it.id.toString(),
                    name = it.name,
                    uid = it.uid,
                    email = it.email,
                    role = it.role,
                    createdAt = formatTimestamp(it.createdAt),
                    updatedAt = formatTimestamp(it.updatedAt)
                )
            }
            emit(ResultWrapper.Success(user))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar utilizador"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createUser(uid: String, name: String, email: String, passwordHash: String, role: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createUser(uid, name, email, passwordHash, role)
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar utilizador"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteUser(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteUser(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao eliminar utilizador"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateUser(id: String, uid: String?, name: String?, email: String?, role: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateUser(id, uid, name, email, role)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar utilizador"))
        }
    }.flowOn(Dispatchers.IO)

    private fun formatTimestamp(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}