package com.mooveit.kotlin.kotlintemplateproject.presentation.internal.di.module

import com.mooveit.kotlin.kotlintemplateproject.data.network.PetStoreService
import com.mooveit.kotlin.kotlintemplateproject.data.repository.PetRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun providePetRepository(service: PetStoreService): PetRepository {
        return PetRepository(service)
    }
}
