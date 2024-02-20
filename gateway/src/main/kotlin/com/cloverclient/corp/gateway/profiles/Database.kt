package com.cloverclient.corp.gateway.profiles

import com.cloverclient.corp.core.services.Database
import com.cloverclient.corp.gateway.protocol.profiles.Profile
import io.ktor.server.application.*
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

/**
 * @author GrowlyX
 * @since 2/19/2024
 */
private lateinit var profilesTable: DynamoDbAsyncTable<Profile>
fun Application.configureProfilesDatabase()
{
    val configTableName = environment.config
        .property("aws.dynamodb.tables.profiles")
        .getString()
    profilesTable = Database.client()
        .table(
            configTableName,
            TableSchema.fromBean(Profile::class.java)
        )

    profilesTable.describeTable().thenAccept {
        log.info("Using profiles table: ${it.table().tableArn()}")
    }
}

internal fun profilesTable() = profilesTable
