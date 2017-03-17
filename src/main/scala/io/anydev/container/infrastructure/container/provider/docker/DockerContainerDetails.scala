package io.anydev.container.infrastructure.container.provider.docker

case class DockerContainerDetails(Id: String, Image: String, Created: String, State: DockerContainerState)
