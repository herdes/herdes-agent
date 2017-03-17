package io.anydev.container.interfaces

import akka.actor.ActorRef
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatchers, Route, StandardRoute}
import akka.pattern.ask
import io.herdes.agent.sdk._

import scala.concurrent.Future
import scala.concurrent.duration._

object ContainerRoute {

  def route(containerServiceActor: ActorRef): Route = {
    pathPrefix("container") {
      pathPrefix(PathMatchers.Segment) {
        id => {
          get {
            val response = sendProtocolMessage(containerServiceActor, InspectContainer(id))
            onSuccess(response)(r => completeWithToString(r))
          } ~
          delete {
            val response = sendProtocolMessage(containerServiceActor, DeleteContainer(id))
            onSuccess(response)(r => completeWithToString(r))
          } ~
          path("start") {
            post {
              parameters('image, 'name) { (image, name) =>
                val response = sendProtocolMessage(containerServiceActor, StartContainer(id))
                onSuccess(response)(r => completeWithToString(r))
              }
            }
          } ~
          path("stop") {
            post {
              val response = sendProtocolMessage(containerServiceActor, StopContainer(id))
              onSuccess(response)(r => completeWithToString(r))
            }
          } ~
          path("restart") {
            post {
              val response = sendProtocolMessage(containerServiceActor, RestartContainer(id))
              onSuccess(response)(r => completeWithToString(r))
            }
          } ~
          path("kill") {
            post {
              val response = sendProtocolMessage(containerServiceActor, KillContainer(id))
              onSuccess(response)(r => completeWithToString(r))
            }
          }
        }
      } ~
      get {
        val response = sendProtocolMessage(containerServiceActor, ListContainers())
        onSuccess(response)(r => completeWithToString(r))
      } ~
      post {
        parameters('image, 'entry) { (image, entryPoint) =>
          val response = sendProtocolMessage(containerServiceActor, CreateContainer(ContainerDefinition(image, entryPoint)))
          onSuccess(response)(r => completeWithToString(r))
        }
      }
    }
  }

  def sendProtocolMessage[T](actor: ActorRef, message: ContainerProtocol): Future[T] = {
    actor.ask(message)(5 seconds).asInstanceOf[Future[T]]
  }

  private def completeWithToString(response: Any): StandardRoute = {
    complete {
      response.toString
    }
  }
}
