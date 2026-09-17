package com.example.crud_firebase.di

import com.example.crud_firebase.data.repository.AuthRepositoryImpl
import com.example.crud_firebase.data.repository.DraftRepositoryImpl
import com.example.crud_firebase.data.repository.TaskRepositoryImpl
import com.example.crud_firebase.domain.repository.AuthRepository
import com.example.crud_firebase.domain.repository.DraftRepository
import com.example.crud_firebase.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindDraftRepository(
        draftRepositoryImpl: DraftRepositoryImpl
    ): DraftRepository
}