plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "cloverclient-services-monorepo"
include("gateway", "websocket-protocol", "core")
