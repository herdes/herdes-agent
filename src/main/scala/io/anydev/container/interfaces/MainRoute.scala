package io.anydev.container.interfaces

import akka.actor.ActorRef
import akka.http.scaladsl.server.RouteConcatenation

object MainRoute extends RouteConcatenation {

  def route(containerServiceActor: ActorRef) = {
    ContainerRoute.route(containerServiceActor)
  }
}
