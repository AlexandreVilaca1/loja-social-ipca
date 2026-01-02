package com.example.sas.di

import com.example.sas.data.datasource.AuthDataSource
import com.example.sas.data.datasource.BeneficiariesDataSource
import com.example.sas.data.repository.AuthRepositoryImpl
import com.example.sas.data.repository.BeneficiariesRepositoryImpl
import com.example.sas.domain.repositories.AuthRepository
import com.example.sas.domain.repositories.BeneficiariesRepository
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

    @Provides
    @Singleton
    fun provideBeneficiariesDataSource(): BeneficiariesDataSource {
        return BeneficiariesDataSource()
    }
}
