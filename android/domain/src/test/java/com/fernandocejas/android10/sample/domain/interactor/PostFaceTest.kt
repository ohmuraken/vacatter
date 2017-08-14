package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.android10.sample.domain.interactor.PostFace.Params
import com.fernandocejas.android10.sample.domain.repository.FaceRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI

@RunWith(MockitoJUnitRunner::class)
class PostFaceTest {
  lateinit var mockThreadExecutor: ThreadExecutor
  lateinit var mockPostExecutionThread: PostExecutionThread
  lateinit var mockFaceRepository: FaceRepository
  lateinit var postFace: PostFace

  @Before
  fun setUp() {
    mockThreadExecutor = Mockito.mock(ThreadExecutor::class.java)
    mockPostExecutionThread = Mockito.mock(PostExecutionThread::class.java)
    mockFaceRepository = Mockito.mock(FaceRepository::class.java)
    postFace = PostFace(mockFaceRepository, mockThreadExecutor, mockPostExecutionThread)
  }

  @Test
  fun testPostFaceUseCaseObserverHappyCase() {
    postFace.buildUseCaseCompletable(Params.forFace(URI("TEST")))

    Mockito.verify(mockFaceRepository).postFace(URI("TEST"))
  }
}