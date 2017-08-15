package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import com.fernandocejas.android10.sample.data.net.RetroApi
import com.nhaarman.mockito_kotlin.given
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.io.File
import java.net.URI

/**
 *
 *
 * Created on 8/13/17.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(Context::class, RetroApi::class, MultipartBody.Part::class, DataStoreHelper::class)
class FaceCloudDataStoreTest {
  val TEST_TOKEN: String = "HOGEHOGE"
  val mockApi: RetroApi = PowerMockito.mock(RetroApi::class.java)
  val mockFile: File = PowerMockito.mock(File::class.java)
  val mockRequestBody: RequestBody = PowerMockito.mock(RequestBody::class.java)
  val mockBody: MultipartBody.Part = PowerMockito.mock(MultipartBody.Part::class.java)
  val mockURI: URI = PowerMockito.mock(URI::class.java)
  val mockDataStoreHelper: DataStoreHelper = PowerMockito.mock(DataStoreHelper::class.java)

  lateinit var faceCloudDataStore: FaceCloudDataStore

//  @Before
//  fun setUp() {
//    faceCloudDataStore = FaceCloudDataStore(mockApi, mockDataStoreHelper)
////    given(mockDataStoreHelper.getToken()).willReturn(TEST_TOKEN)
////    given(mockDataStoreHelper.convertFile(mockURI)).willReturn(mockFile)
////    given(mockDataStoreHelper.createRequestFile(mockFile)).willReturn(mockRequestBody)
////    given(mockDataStoreHelper.createMultipartBody(mockFile, mockRequestBody)).willReturn(mockBody)
//  }
//
//  @Test
//  fun testFaceCloudDataStoreHappyCase() {
//    faceCloudDataStore.postFace(mockURI)
//  }
}