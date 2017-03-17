package io.anydev.container.infrastructure.container.provider

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe
import akka.stream.ActorMaterializer
import io.anydev.container.infrastructure.container.provider.docker.DockerProvider
import io.herdes.agent.sdk._
import io.scalaberries.akka.{ActorEmptyReceive, ActorFactory}

class ContainerProviderActor extends Actor with ActorLogging with ActorEmptyReceive {
  implicit val system = context.system
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
  private val dockerEngine: DockerProvider = new DockerProvider(context)

  override def receive: Receive = {
    case ListContainers() =>
      dockerEngine.listContainers() pipeTo sender

    case CreateContainer(definition) =>
      dockerEngine.createContainer(definition) pipeTo sender

    case StartContainer(id) =>
      dockerEngine.startContainer(id) pipeTo sender

    case InspectContainer(id) =>
      dockerEngine.inspectContainer(id) pipeTo sender

    case StopContainer(id) =>
      dockerEngine.stopContainer(id) pipeTo sender

    case _ =>
  }
}

object ContainerProviderActor extends ActorFactory {
  override implicit val actorClass = classOf[ContainerProviderActor]
  override implicit val actorName = "container-engine-actor"
}
