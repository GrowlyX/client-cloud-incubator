application {
    mainClass.set("com.cloverclient.corp.gateway.ApplicationKt")
}

dependencies {
    api(project(":core"))
    api("io.ktor:ktor-server-websockets")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.7")
}

ktor {
    fatJar {
        archiveFileName.set("gateway.jar")
    }
}
