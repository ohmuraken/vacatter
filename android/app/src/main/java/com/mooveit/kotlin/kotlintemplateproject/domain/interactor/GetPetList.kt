package com.mooveit.kotlin.kotlintemplateproject.domain.interactor

import com.mooveit.kotlin.kotlintemplateproject.data.entity.Pet
import com.mooveit.kotlin.kotlintemplateproject.data.repository.PetRepository
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.ThreadExecutor

import io.reactivex.Observable
import javax.inject.Inject

class GetPetList @Inject constructor(private val mRepository: PetRepository,
                                     threadExecutor: ThreadExecutor,
                                     postExecutionThread: PostExecutionThread) :
        UseCase<List<Pet>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Observable<List<Pet>> {
        return mRepository.petsAvailable()
    }
}