package com.cloverclient.corp.gateway.profiles

import com.cloverclient.corp.core.services.Database
import io.ktor.server.application.*
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.TableMetadata
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument

/**
 * @author GrowlyX
 * @since 2/19/2024
 */
private lateinit var profilesTable: DynamoDbAsyncTable<EnhancedDocument>
fun Application.configureProfilesDatabase()
{
    val configTableName = environment.config
        .property("aws.dynamodb.tables.profiles")
        .getString()

    profilesTable = Database.client()
        .table(
            configTableName,
            TableSchema.documentSchemaBuilder()
                .addIndexPartitionKey(TableMetadata.primaryIndexName(),"profileId", AttributeValueType.S)
                .build()
        )

    profilesTable.describeTable().thenAccept {
        log.info("Using profiles table: ${it.table().tableArn()}")
    }
}

internal fun profilesTable() = profilesTable
