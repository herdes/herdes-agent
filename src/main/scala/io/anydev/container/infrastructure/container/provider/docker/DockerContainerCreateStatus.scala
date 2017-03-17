package io.anydev.container.infrastructure.container.provider.docker

case class DockerContainerCreateStatus(Id: String, ImageID: String, Image: String, Names: Array[String], State: String, description: Option[String] = Option.empty)
