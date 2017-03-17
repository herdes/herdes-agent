package io.anydev.container

import io.anydev.shared.UuidGenerator

object TestUtils {

  def randomString: String = {
    UuidGenerator.generate().toString
  }
}
