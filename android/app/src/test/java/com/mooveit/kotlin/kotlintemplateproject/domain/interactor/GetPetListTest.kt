package com.mooveit.kotlin.kotlintemplateproject.domain.interactor

import com.mooveit.kotlin.kotlintemplateproject.data.network.PetStoreService
import com.mooveit.kotlin.kotlintemplateproject.data.repository.PetRepository
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.ThreadExecutor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPetListTest {

    @Mock
    private lateinit var mockThreadExecutor: ThreadExecutor
    @Mock
    private lateinit var mockPostExecutionThread: PostExecutionThread
    @Mock
    private lateinit var mockPetService: PetStoreService
    @Mock
    private lateinit var mockPetRepository: PetRepository
    
    private lateinit var getPetList: GetPetList

    @Before
    fun setUp() {
        getPetList = GetPetList(mockPetRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun getPets_forwardsRequestToRepository() {
        getPetList.buildUseCaseObservable(null)

        verify<PetRepository>(mockPetRepository).petsAvailable()
        verifyNoMoreInteractions(mockPetRepository)
        verifyZeroInteractions(mockThreadExecutor)
        verifyZeroInteractions(mockPostExecutionThread)
    }
}