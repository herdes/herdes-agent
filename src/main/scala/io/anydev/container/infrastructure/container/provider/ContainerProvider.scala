package io.anydev.container.infrastructure.container.provider

import io.herdes.agent.sdk._

import scala.concurrent.Future

trait ContainerProvider {
  def createContainer(containerDefinition: ContainerDefinition): Future[String]
  def startContainer(id: String): Future[Any]
  def stopContainer(id: String): Future[Any]
}
