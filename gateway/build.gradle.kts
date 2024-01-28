application {
    mainClass.set("com.cloverclient.corp.gateway.ApplicationKt")
}

dependencies {
    api("io.ktor:ktor-server-websockets")
    api("build.buf.gen:cloverclient_gateway_protocolbuffers_kotlin:${rootProject.libs.versions.protobufs.gateway.orNull}")
}

ktor {
    fatJar {
        archiveFileName.set("gateway.jar")
    }
}
