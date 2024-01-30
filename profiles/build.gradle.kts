application {
    mainClass.set("com.cloverclient.corp.profiles.ApplicationKt")
}

dependencies {
    api(project(":core"))
}

ktor {
    fatJar {
        archiveFileName.set("gateway.jar")
    }
}
