package io.anydev.container

import akka.actor.ActorSystem

object Launcher extends App {
  implicit val system = ActorSystem("herdes-agent")
  val topSupervisor = TopSupervisor.actor
}
