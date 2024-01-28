plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.cloverclient.corp.services"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    configureBufBuildRepository()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

fun RepositoryHandler.configureBufBuildRepository()
{
    maven {
        name = "cloverBuf"
        url = uri("https://buf.build/gen/maven")
        credentials(HttpHeaderCredentials::class)
        authentication {
            create<HttpHeaderAuthentication>("header")
        }
    }
}
