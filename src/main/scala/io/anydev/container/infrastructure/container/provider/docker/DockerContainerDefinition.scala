package io.anydev.container.infrastructure.container.provider.docker

case class DockerContainerDefinition(Image: String, EntryPoint: String, Tty: Boolean = true)
