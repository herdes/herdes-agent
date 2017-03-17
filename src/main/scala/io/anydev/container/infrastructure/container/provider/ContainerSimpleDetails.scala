package io.anydev.container.infrastructure.container.provider

case class ContainerSimpleDetails(id: String, imageId: String, name: String, state: String, description: Option[String] = Option.empty)
