import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.21"
    id("org.ajoberstar.grgit") version "4.1.1"
}

var currentBranch: String = grgit.branch.current().name

allprojects {
    group = "com.cloverclient.corp.services"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        bufBuild()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        compileOnly(kotlin("stdlib"))
    }

    kotlin {
        jvmToolchain(17)
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
