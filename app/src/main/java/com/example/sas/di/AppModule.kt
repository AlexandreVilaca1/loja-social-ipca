package com.example.sas.di

import com.example.sas.data.datasource.AuthDataSource
import com.example.sas.data.repository.AuthRepositoryImpl
import com.example.sas.data.repository.BeneficiariesRepositoryImpl
import com.example.sas.data.repository.DistributionItemsRepositoryImpl
import com.example.sas.data.repository.DistributionsRepositoryImpl
import com.example.sas.data.repository.LotsRepositoryImpl
import com.example.sas.data.repository.StatusTypesRepositoryImpl
import com.example.sas.data.repository.UsersRepositoryImpl
import com.example.sas.domain.repositories.AuthRepository
import com.example.sas.domain.repositories.BeneficiariesRepository
import com.example.sas.domain.repositories.DistributionItemsRepository
import com.example.sas.domain.repositories.DistributionsRepository
import com.example.sas.domain.repositories.LotsRepository
import com.example.sas.domain.repositories.StatusTypesRepository
import com.example.sas.domain.repositories.UsersRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindBeneficiariesRepository(
        impl: BeneficiariesRepositoryImpl
    ): BeneficiariesRepository

    @Binds
    @Singleton
    abstract fun bindDistributionsRepository(
        impl: DistributionsRepositoryImpl
    ): DistributionsRepository

    @Binds
    @Singleton
    abstract fun bindDistributionItemsRepository(
        impl: DistributionItemsRepositoryImpl
    ): DistributionItemsRepository

    @Binds
    @Singleton
    abstract fun bindUsersRepository(
        impl: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    @Singleton
    abstract fun bindStatusTypesRepository(
        impl: StatusTypesRepositoryImpl
    ): StatusTypesRepository

    @Binds
    @Singleton
    abstract fun bindLotsRepository(
        impl: LotsRepositoryImpl
    ): LotsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AppProvidesModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthDataSource(auth: FirebaseAuth): AuthDataSource {
        return AuthDataSource(auth)
    }
}
