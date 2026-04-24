package com.lfp.gitscope.di

import com.lfp.gitscope.data.repository.GithubRepositoryImpl
import com.lfp.gitscope.domain.repository.GithubRepository
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
    abstract fun bindGithubRepository(
        impl: GithubRepositoryImpl
    ): GithubRepository
}