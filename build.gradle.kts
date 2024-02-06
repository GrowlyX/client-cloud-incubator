import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version libs.versions.kotlin
    kotlin("kapt") version libs.versions.kotlin
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
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "io.ktor.plugin")
    apply(plugin = "application")

    dependencies {
        kapt("org.glassfish.hk2:hk2-metadata-generator:${rootProject.libs.versions.hk2.orNull}")
        api(kotlin("stdlib"))

        api("io.ktor:ktor-server-core")
        api("io.ktor:ktor-server-netty")
        api("io.ktor:ktor-server-config-yaml")
        api("io.ktor:ktor-server-content-negotiation")
        api("io.ktor:ktor-serialization-kotlinx-json")
        api("io.ktor:ktor-server-auth")
        api("io.ktor:ktor-server-auth-jwt")

        api("ch.qos.logback:logback-classic:${rootProject.libs.versions.logback.orNull}")

        api("org.jetbrains.kotlinx:kotlinx-serialization-json:${rootProject.libs.versions.serialization.orNull}")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.libs.versions.coroutines.orNull}")

        api("aws.sdk.kotlin:kinesis:${awsSDKVersion()}")
        api("aws.sdk.kotlin:elasticache:${awsSDKVersion()}")
        api("aws.sdk.kotlin:dynamodb:${awsSDKVersion()}")

        testApi("io.ktor:ktor-server-test-host-jvm")
        testApi("io.ktor:ktor-client-content-negotiation")
        testApi("io.ktor:ktor-serialization-kotlinx-json")
        testApi("org.jetbrains.kotlin:kotlin-test-junit")
    }

    kapt {
        keepJavacAnnotationProcessors = true
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
