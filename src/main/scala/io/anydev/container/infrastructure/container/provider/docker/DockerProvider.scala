package io.anydev.container.infrastructure.container.provider.docker

import akka.actor.ActorContext
import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model.{HttpRequest, _}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import io.anydev.container.infrastructure.config.ConfigManager
import io.anydev.container.infrastructure.container.provider.{ContainerProvider, ContainerSimpleDetails}
import io.herdes.agent.sdk._
import net.liftweb.json._

import scala.concurrent.Future

class DockerProvider(context: ActorContext) extends ContainerProvider {

  implicit val system = context.system
  implicit val executionContext = context.dispatcher
  implicit val materializer = ActorMaterializer()

  def listContainers(): Future[List[ContainerSimpleDetails]] = {
    val containersUrl = ConfigManager.dockerContainersListUrl
    val request = HttpRequest(uri = containersUrl).withEntity(HttpEntity.Empty)
    val responseFuture = DockerConnector.sendRequest(request)
    responseFuture.flatMap(Unmarshal(_).to[String]).map(parse).map(_.children.map(mapJsonToContainerSimpleDetails))
  }

  def createContainer(containerDefinition: ContainerDefinition): Future[String] = {
    val containersUrl = ConfigManager.dockerContainersAddUrl
    val requestBody = mapContainerSpecificationToJson(containerDefinition)
    val requestEntity = HttpEntity(ContentType(MediaTypes.`application/json`), requestBody)
    val request = HttpRequest(method = POST, uri = containersUrl).withEntity(requestEntity)
    val responseFuture = DockerConnector.sendRequest(request)
    responseFuture.flatMap(Unmarshal(_).to[String]).map(parse).map(_ \\ "Id").map(render).map(compact)
  }

  def startContainer(id: String): Future[Any] = {
    sendEmptyPost(ConfigManager.dockerContainersStartUrl(id))
  }

  def stopContainer(id: String): Future[Any] = {
    sendEmptyPost(ConfigManager.dockerContainersStopUrl(id))
  }

  def inspectContainer(id: String): Future[DockerContainerDetails] = {
    val responseFuture = sendEmptyGet(ConfigManager.dockerContainersInspectUrl(id))
    responseFuture.flatMap(Unmarshal(_).to[String]).map(parse).map(mapJsonToContainerDetails)
  }

  def sendEmptyGet(containersUrl: Uri): Future[HttpResponse] = {
    sendEmptyRequest(GET, containersUrl)
  }

  def sendEmptyPost(containersUrl: Uri): Future[HttpResponse] = {
    sendEmptyRequest(POST, containersUrl)
  }

  def sendEmptyRequest(withMethod: HttpMethod, containersUrl: Uri): Future[HttpResponse] = {
    val request = HttpRequest(method = withMethod, uri = containersUrl).withEntity(HttpEntity.Empty)
    DockerConnector.sendRequest(request)
  }

  def mapJsonToContainerSimpleDetails(jsonObject: JValue): ContainerSimpleDetails = {
    implicit val formats = DefaultFormats
    jsonObject.extract[DockerContainerCreateStatus]
  }

  def mapJsonToContainerDetails(jsonObject: JValue): DockerContainerDetails = {
    implicit val formats = DefaultFormats
    jsonObject.extract[DockerContainerDetails]
  }

  def mapContainerSpecificationToJson(specification: DockerContainerDefinition) = {
    implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[DockerContainerDefinition])))
    Serialization.write(specification)
  }
}
