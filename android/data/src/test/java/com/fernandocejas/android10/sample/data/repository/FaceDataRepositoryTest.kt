package com.fernandocejas.android10.sample.data.repository

import com.fernandocejas.android10.sample.data.repository.datasource.FaceCloudDataStore
import com.fernandocejas.android10.sample.data.repository.datasource.FaceDataStoreFactory
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.net.URI

/**
 *
 *
 * Created on 8/13/17.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(FaceDataStoreFactory::class, FaceCloudDataStore::class)
class FaceDataRepositoryTest {

  val mockFaceDataStoreFactory: FaceDataStoreFactory = PowerMockito.mock(
      FaceDataStoreFactory::class.java)
  val mockFaceCloudDataStore: FaceCloudDataStore = PowerMockito.mock(
      FaceCloudDataStore::class.java)
  lateinit var faceDataRepository: FaceDataRepository

  @Before
  fun setUp() {
    given(mockFaceDataStoreFactory.createCloudDataStore()).willReturn(mockFaceCloudDataStore)
    faceDataRepository = FaceDataRepository(mockFaceDataStoreFactory)
  }

  @Test
  fun testPostFaceHappyCase() {
    faceDataRepository.postFace(URI("aaa"))

    verify(mockFaceDataStoreFactory).createCloudDataStore()
  }
}