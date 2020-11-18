package org.arhor.diploma.startup

import org.arhor.diploma.commons.Priority
import org.arhor.diploma.commons.StartupTask
import org.arhor.diploma.util.createLogger
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.net.InetAddress
import java.net.UnknownHostException

@Component
class DisplayAppInfo(private val env: Environment) : StartupTask {

    override val priority = Priority.LAST

    override fun execute() {
        val appName = env.getProperty("spring.application.name")
        val serverPort = env.getProperty("server.port")
        val contextPath = env.getProperty("server.servlet.context-path")?.takeIf { it.isNotBlank() } ?: "/"

        val protocol = when (env.getProperty("server.ssl.key-store")) {
            null -> "http"
            else -> "https"
        }

        val hostAddress = try {
            InetAddress.getLocalHost().hostAddress
        } catch (e: UnknownHostException) {
            log.warn("The host name could not be determined, using `localhost` as fallback")
            "localhost"
        }

        log.info(
            """
--------------------------------------------------------------------------------
    Application `${appName}` is running! Access URLs:
    - Local:      ${protocol}://localhost:${serverPort}${contextPath}
    - External:   ${protocol}://${hostAddress}:${serverPort}${contextPath}
    - java ver.: ${System.getProperty("java.version")}
--------------------------------------------------------------------------------"""
        )
    }

    companion object {
        @JvmStatic
        private val log = createLogger<DisplayAppInfo>()
    }
}