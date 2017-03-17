package io.anydev.container.infrastructure.container.provider.docker

class NameNormalizer(val name: String) {
  def normalize: String = {
    name match {
      case tailName if tailName.startsWith("/") => tailName.substring(1)
      case _ => name
    }
  }
}

class NamesArrayNormalizer(val names: Array[String]) {
  def firstItemOrDefault(default: String) = {
    names.headOption.getOrElse(default).normalize
  }
}

object NamesArrayNormalizer {
  def apply(names: Array[String]) = {
    new NamesArrayNormalizer(names)
  }
}

object NameNormalizer {
  def apply(name: String): NameNormalizer = {
    new NameNormalizer(name)
  }
}