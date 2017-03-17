package io.anydev.container.infrastructure.container.provider.docker

import javax.net.ssl.SSLContext

import akka.actor.ActorSystem
import akka.http.scaladsl.{Http, HttpsConnectionContext}
import akka.http.scaladsl.model.HttpRequest
import akka.stream.Materializer

object DockerConnector {
  def sendRequest(request: HttpRequest)(implicit system: ActorSystem, materializer: Materializer) = {
    val connectionContext = createConnectionContext
    Http().singleRequest(request, connectionContext)
  }

  private def createConnectionContext = {
    new HttpsConnectionContext(SSLContext.getDefault)
  }
}
