package com.cloverclient.corp.gateway.profiles

import com.cloverclient.corp.gateway.protocol.profiles.Profile
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import software.amazon.awssdk.enhanced.dynamodb.Key
import java.util.*

/**
 * @author GrowlyX
 * @since 2/19/2024
 */
fun Application.configureProfilesRouting()
{
    val applicationLog = log
    routing {
        route("/api/profiles/") {
            get("/test") {
                val profileId = UUID.randomUUID()
                profilesTable()
                    .putItem(
                        Profile(profileId, "testing")
                    )
                    .thenAccept {
                        applicationLog.info("Inserted a testing profile with ID $profileId")
                    }
                    .join()

                val getItemResponse = profilesTable()
                    .getItem(
                        Key.builder()
                            .partitionValue(profileId.toString())
                            .build()
                    )
                    .join()

                call.respond(getItemResponse)
            }
        }
    }
}
