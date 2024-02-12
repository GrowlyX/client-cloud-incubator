apply(plugin = "application")

dependencies {
    api(project(":core"))
    api(project(":websocket-protocol"))
    api("io.ktor:ktor-server-websockets")
}

application {
    mainClass.set("com.cloverclient.corp.gateway.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

ktor {
    fatJar {
        archiveFileName.set("gateway.jar")
    }
}
