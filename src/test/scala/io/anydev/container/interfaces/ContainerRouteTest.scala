package io.anydev.container.interfaces

import akka.actor.Actor
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestActorRef
import io.anydev.container.interfaces.ContainerRouteTest._
import io.herdes.agent.sdk._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class ContainerRouteTest extends FlatSpec with GivenWhenThen with ScalatestRouteTest with MockitoSugar with Matchers {
  val containerServiceActorRef = TestActorRef[MockedContainerServiceActor]
  val route = ContainerRoute.route(containerServiceActorRef)

  "get to /container" should "return a list of containers" in {
    Get("/container") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerList
    }
  }

  "post to /container" should "create a container with given image and entry point" in {
    Post(s"/container?image=$TestImageName&entry=$TestEntryPoint") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerId
    }
  }

  "get to /container/id" should "return details of a container with given id" in {
    Get(s"/container/$TestContainerId") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerDetails
    }
  }

  "delete to /container/id" should "delete a container with given id" in {
    Delete(s"/container/$TestContainerId") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerDeletionStatus
    }
  }

  "post to /container/id/start" should "start a container with given id" in {
    Post(s"/container/$TestContainerId/start") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerStartStatus
    }
  }

  "post to /container/id/stop" should "stop a container with given id" in {
    Post(s"/container/$TestContainerId/stop") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerStopStatus
    }
  }

  "post to /container/id/restart" should "restart a container with given id" in {
    Post(s"/container/$TestContainerId/restart") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerRestartStatus
    }
  }

  "post to /container/id/kill" should "kill a container with given id" in {
    Post(s"/container/$TestContainerId/kill") ~> route ~> check {
      entityAs[String] shouldEqual TestContainerKillStatus
    }
  }
}

object ContainerRouteTest {
  val TestContainerId = "containerId"
  val TestHostName = "hostName"
  val TestImageName = "imageName"
  val TestEntryPoint = "entryPoint"
  val TestContainerList = "container list"
  val TestContainerDetails = "container details"
  val TestContainerDeletionStatus = "container deletion status"
  val TestContainerStartStatus = "container start status"
  val TestContainerStopStatus = "container stop status"
  val TestContainerRestartStatus = "container restart status"
  val TestContainerKillStatus = "container kill status"
}

class MockedContainerServiceActor extends Actor {
  override def receive: Receive = {
    case ListContainers() => sender ! TestContainerList
    case CreateContainer(ContainerDefinition(TestImageName, "entryPoint")) => sender ! TestContainerId
    case StartContainer(TestContainerId) => sender ! TestContainerStartStatus
    case StopContainer(TestContainerId) => sender ! TestContainerStopStatus
    case RestartContainer(TestContainerId) => sender ! TestContainerRestartStatus
    case KillContainer(TestContainerId) => sender ! TestContainerKillStatus
    case InspectContainer(TestContainerId) => sender ! TestContainerDetails
    case DeleteContainer(TestContainerId) => sender ! TestContainerDeletionStatus
  }
}
