package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.repository.FaceRepository
import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CompletableUseCaseTest {
  lateinit var mockThreadExecutor: ThreadExecutor
  lateinit var mockPostExecutionThread: PostExecutionThread
  lateinit var mockFaceRepository: FaceRepository

  // Test Target
  lateinit var completableUseCase: TestCompletableUseCaseClass
  lateinit var testObserver: TestDisposableCompletableObserver

  @Before
  fun setUp() {
    this.mockThreadExecutor = Mockito.mock(ThreadExecutor::class.java)
    this.mockPostExecutionThread = Mockito.mock(PostExecutionThread::class.java)
    this.completableUseCase = TestCompletableUseCaseClass(mockThreadExecutor,
        mockPostExecutionThread)
    given(mockPostExecutionThread.getScheduler()).willReturn(TestScheduler())
    this.testObserver = TestDisposableCompletableObserver()
  }

  @Test
  fun testBuildUseCaseCompletableReturnCorrectResult() {
    completableUseCase.execute(testObserver, Params.EMPTY)

    assertThat(testObserver.valuesCount).isZero()
  }

  @Test
  fun testSubscriptionWhenExecutingUseCase() {
    completableUseCase.execute(testObserver, Params.EMPTY)
    completableUseCase.dispose()

    assertThat(testObserver.isDisposed).isTrue()
  }

  class TestCompletableUseCaseClass constructor(override val threadExecutor: ThreadExecutor,
      override val postExecutionThread: PostExecutionThread) : CompletableUseCase<Params>(
      threadExecutor, postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params): Completable {
      return Completable.never()
    }
  }

  class TestDisposableCompletableObserver : DisposableCompletableObserver() {
    var valuesCount: Int = 0
    override fun onComplete() {}
    override fun onError(e: Throwable?) {}
  }

  class Params {
    companion object {
      val EMPTY: Params = Params()
    }
  }
}