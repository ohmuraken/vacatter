package com.fernandocejas.android10.sample.domain

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.net.URI

class FaceTest {
  val FAKE_IMAGE_URI: URI = URI("Test")
  lateinit var face: Face

  @Before
  fun setUp() {
    this.face = Face(this.FAKE_IMAGE_URI)
  }

  @Test
  fun testFaceConstructorHappyCase() {
    val faceImageURI :URI = face.photo
    Assertions.assertThat(FAKE_IMAGE_URI).isEqualTo(faceImageURI)
  }
}