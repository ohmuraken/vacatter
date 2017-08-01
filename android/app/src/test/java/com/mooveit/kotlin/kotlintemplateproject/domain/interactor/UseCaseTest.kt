package com.mooveit.kotlin.kotlintemplateproject.domain.interactor

import org.assertj.core.api.Assertions.assertThat
import org.mockito.BDDMockito.given

@org.junit.runner.RunWith(org.mockito.runners.MockitoJUnitRunner::class)
class UseCaseTest {

    private var useCase: com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.UseCaseTestClass? = null

    private var testObserver: com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.TestDisposableObserver<Any>? = null

    @org.mockito.Mock
    private lateinit var mockThreadExecutor: com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.ThreadExecutor
    @org.mockito.Mock
    private lateinit var mockPostExecutionThread: com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread

    @get:org.junit.Rule
    var expectedException = org.junit.rules.ExpectedException.none()

    @org.junit.Before
    fun setUp() {
        this.useCase = com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread)
        this.testObserver = com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.TestDisposableObserver<Any>()
        given<io.reactivex.Scheduler>(mockPostExecutionThread.scheduler).willReturn(io.reactivex.schedulers.TestScheduler())
    }

    @org.junit.Test
    fun testBuildUseCaseObservableReturnCorrectResult() {
        useCase!!.execute(testObserver!!, com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.Params.EMPTY)

        assertThat(testObserver!!.valuesCount).isZero
    }

    @org.junit.Test
    fun testSubscriptionWhenExecutingUseCase() {
        useCase!!.run {
            execute(testObserver!!, com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.Params.EMPTY)
            dispose()
        }

        assertThat(testObserver!!.isDisposed).isTrue
    }

    @Suppress("UNREACHABLE_CODE")
    @org.junit.Test
    fun testShouldFailWhenExecuteWithNullObserver() {
        expectedException.expect(NullPointerException::class.java)
        useCase!!.execute(null!!, com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.Params.EMPTY)
    }

    private class UseCaseTestClass internal constructor(threadExecutor: com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.ThreadExecutor, postExecutionThread: com.mooveit.kotlin.kotlintemplateproject.domain.excecutor.PostExecutionThread)
        : com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCase<Any, Params>(threadExecutor, postExecutionThread) {

        override fun buildUseCaseObservable(params: com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.Params?): io.reactivex.Observable<Any> {
            return io.reactivex.Observable.empty<Any>()
        }

        override fun execute(observer: io.reactivex.observers.DisposableObserver<Any>, params: com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.Params?) {
            super.execute(observer, params)
        }
    }

    private class TestDisposableObserver<T> : io.reactivex.observers.DisposableObserver<T>() {
        internal var valuesCount = 0

        override fun onNext(value: T) {
            valuesCount++
        }

        override fun onError(e: Throwable) {
            // no-op by default.
        }

        override fun onComplete() {
            // no-op by default.
        }
    }

    private object Params {
        internal val EMPTY
            get() = com.mooveit.kotlin.kotlintemplateproject.domain.interactor.UseCaseTest.Params
    }
}
