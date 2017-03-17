package io.anydev.container

import akka.actor.SupervisorStrategy.{Escalate, Stop}
import akka.actor.{Actor, ActorInitializationException, ActorKilledException, ActorLogging, AllDeadLetters, OneForOneStrategy, SupervisorStrategy}
import io.anydev.container.infrastructure.endpoint.HttpServerActor
import io.scalaberries.akka.{ActorEmptyReceive, ActorFactory}

class TopSupervisor extends Actor with ActorLogging with ActorEmptyReceive {
  private val system = context.system
  HttpServerActor.actor

  override val supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _: ActorKilledException => Escalate
    case _: ActorInitializationException => Escalate
    case _ => Stop
  }

  override def preStart(): Unit = {
    system.eventStream.subscribe(self, classOf[AllDeadLetters])
  }

  override def receive: Receive = {
    case any =>
  }
}

object TopSupervisor extends ActorFactory {
  override implicit val actorClass = classOf[TopSupervisor]
  override implicit val actorName = "top-supervisor-actor"
}