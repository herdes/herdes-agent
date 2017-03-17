package io.anydev.container.infrastructure.container.provider.docker

case class DockerContainerState(Error: String,
                                ExitCode: Int,
                                FinishedAt: String,
                                OOMKilled: Boolean,
                                Dead: Boolean,
                                Paused: Boolean,
                                Pid: Long,
                                Restarting: Boolean,
                                Running: Boolean,
                                StartedAt: String,
                                Status: String)
