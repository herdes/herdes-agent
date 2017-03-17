package io.anydev.container.infrastructure.config

import akka.http.scaladsl.model.Uri
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._

object ConfigManager {
  private val AkkaHttpHost: String = "akka.http.host"
  private val AkkaHttpPort: String = "akka.http.port"
  private val AkkaDockerContainersListUrl: String = "akka.docker.containers.list.url"
  private val AkkaDockerContainersAddUrl: String = "akka.docker.containers.add.url"
  private val AkkaDockerContainersStartUrl: String = "akka.docker.containers.start.url"
  private val AkkaDockerContainersStopUrl: String = "akka.docker.containers.stop.url"
  private val AkkaDockerContainersInspectUrl: String = "akka.docker.containers.inspect.url"

  private val fallBackProperties = Map(AkkaHttpHost -> "localhost", AkkaHttpPort -> 8080).asJava
  private val fallBackConfig = ConfigFactory.parseMap(fallBackProperties)
  private val config = ConfigFactory.load().withFallback(fallBackConfig)

  lazy val httpHost: String = config.getString(AkkaHttpHost)
  lazy val httpPort: Int = config.getInt(AkkaHttpPort)
  lazy val dockerContainersListUrl: Uri = config.getString(AkkaDockerContainersListUrl)
  lazy val dockerContainersAddUrl: Uri = config.getString(AkkaDockerContainersAddUrl)
  def dockerContainersStartUrl(id: String): Uri = config.getString(AkkaDockerContainersStartUrl).format(id)
  def dockerContainersStopUrl(id: String): Uri = config.getString(AkkaDockerContainersStopUrl).format(id)
  def dockerContainersInspectUrl(id: String): Uri = config.getString(AkkaDockerContainersInspectUrl).format(id)
}
