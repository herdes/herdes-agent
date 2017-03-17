package io.anydev.container.infrastructure.container.provider

import io.herdes.agent.sdk._

package object docker {
  implicit def string2NameNormalizer(name: String): NameNormalizer = NameNormalizer(name)
  implicit def stringArray2NamesArrayNormalizer(names: Array[String]): NamesArrayNormalizer = NamesArrayNormalizer(names)
  
  implicit def toDockerContainerSpecification(source: ContainerDefinition): DockerContainerDefinition = {
    DockerContainerDefinition(source.image, source.entryPoint)
  }

  implicit def toContainerSimpleDetails(source: DockerContainerCreateStatus): ContainerSimpleDetails = {
    val DefaultName: String = "unknown"
    val name: String = source.Names.firstItemOrDefault(DefaultName)
    ContainerSimpleDetails(source.Id, source.ImageID, name, source.State, source.description)
  }
}
