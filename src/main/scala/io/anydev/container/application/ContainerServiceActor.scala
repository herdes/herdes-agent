package io.anydev.container.application

import akka.actor.{Actor, ActorLogging}
import akka.stream.ActorMaterializer
import io.anydev.container.infrastructure.container.provider.ContainerProviderActor
import io.herdes.agent.sdk._
import io.scalaberries.akka.{ActorEmptyReceive, ActorFactory}

class ContainerServiceActor extends Actor with ActorLogging with ActorEmptyReceive {
  implicit val system = context.system
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
  private val providerActor = ContainerProviderActor.actor

  override def postStop(): Unit = {
    log.info("actor stopped")
  }

  override def receive: Receive = {
    case m @ ListContainers() =>
      providerActor forward m

    case m @ CreateContainer(_) =>
      providerActor forward m

    case m @ StartContainer(_) =>
      providerActor forward m

    case m @ StopContainer(_) =>
      providerActor forward m

    case m @ RestartContainer(_) =>
      providerActor forward m

    case m @ KillContainer(_) =>
      providerActor forward m

    case m @ DeleteContainer(_) =>
      providerActor forward m

    case m @ InspectContainer(_) =>
      providerActor forward m

    case _ => 
  }
}

object ContainerServiceActor extends ActorFactory {
  override val actorClass = classOf[ContainerServiceActor]
  override val actorName = "container-service-actor"
}
