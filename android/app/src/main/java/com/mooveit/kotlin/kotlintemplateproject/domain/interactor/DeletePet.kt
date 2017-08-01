package com.mooveit.kotlin.kotlintemplateproject.domain.interactor

import com.mooveit.kotlin.kotlintemplateproject.data.repository.PetRepository
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread
import com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class DeletePet @Inject constructor(private val mRepository: PetRepository,
                                    threadExecutor: ThreadExecutor,
                                    postExecutionThread: PostExecutionThread) :
        UseCase<Response<Void>, Long>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Long?): Observable<Response<Void>> {
        return mRepository.deletePet(params!!)
    }
}