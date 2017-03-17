package io.anydev.container.infrastructure.endpoint

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import io.anydev.container.application.ContainerServiceActor
import io.anydev.container.infrastructure.config.ConfigManager
import io.anydev.container.interfaces.MainRoute
import io.scalaberries.akka.{ActorEmptyReceive, ActorFactory}

class HttpServerActor extends Actor with ActorLogging with ActorEmptyReceive {
  private val DefaultHost = ConfigManager.httpHost
  private val DefaultPort = ConfigManager.httpPort

  implicit val system = context.system
  implicit val materializer = ActorMaterializer()
  private val containerServiceActor = ContainerServiceActor.actor

  override def preStart(): Unit = {
    val route = MainRoute.route(containerServiceActor)
    Http().bindAndHandle(route, DefaultHost, DefaultPort)
  }
}

object HttpServerActor extends ActorFactory {
  override implicit val actorClass = classOf[HttpServerActor]
  override val actorName = "http-server-actor"
}