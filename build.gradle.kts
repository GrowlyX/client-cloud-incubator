import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.serialization") version libs.versions.kotlin
    id("org.ajoberstar.grgit") version libs.versions.grgit
    id("io.ktor.plugin") version libs.versions.ktor
}

var currentBranch: String = grgit.branch.current().name
val awsKotlinSDKVersion =

allprojects {
    group = "com.cloverclient.corp.services"
    version = "1.0-SNAPSHOT"

    repositories {
        bufBuild()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.ktor.plugin")
    apply(plugin = "application")

    dependencies {
        api(kotlin("stdlib"))
        api("io.ktor:ktor-server-core")
        api("io.ktor:ktor-server-netty")
        api("io.ktor:ktor-server-config-yaml")
        api("io.ktor:ktor-server-content-negotiation")
        api("io.ktor:ktor-serialization-kotlinx-protobuf")
        api("io.ktor:ktor-server-auth")
        api("io.ktor:ktor-server-auth-jwt")

        api("ch.qos.logback:logback-classic:${rootProject.libs.versions.logback.orNull}")

        api("org.jetbrains.kotlinx:kotlinx-serialization-json:${rootProject.libs.versions.serialization.orNull}")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.libs.versions.coroutines.orNull}")

        api("aws.sdk.kotlin:kinesis:${awsSDKVersion()}")
        api("aws.sdk.kotlin:elasticache:${awsSDKVersion()}")
        api("aws.sdk.kotlin:dynamodb:${awsSDKVersion()}")
    }

    kotlin {
        jvmToolchain(17)
    }

    application {
        applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
    }

    tasks {
        withType<JavaCompile> {
            options.compilerArgs.add("-parameters")
            options.fork()
            options.encoding = "UTF-8"
        }

        withType<KotlinCompile> {
            kotlinOptions.javaParameters = true
            kotlinOptions.jvmTarget = "17"
        }
    }
}

fun Project.awsSDKVersion() = rootProject.libs.versions.aws.kotlin.sdk.orNull

fun RepositoryHandler.bufBuild()
{
    maven {
        name = "cloverBuf"
        url = uri("https://buf.build/gen/maven")

        credentials(HttpHeaderCredentials::class)
        authentication {
            create<HttpHeaderAuthentication>("header")
        }

        content {
            includeGroup("build.buf.gen")
        }
    }
}
