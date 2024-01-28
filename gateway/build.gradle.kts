application {
    mainClass.set("com.cloverclient.corp.gateway.ApplicationKt")
}

dependencies {
    api("io.ktor:ktor-server-websockets")
}

ktor {
    fatJar {
        archiveFileName.set("gateway.jar")
    }
}
