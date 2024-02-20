package com.cloverclient.corp.gateway.friendships.requests

import com.cloverclient.corp.core.services.Database
import io.ktor.server.application.*
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.TableMetadata
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument

/**
 * @author GrowlyX
 * @since 2/20/2024
 */
private lateinit var friendshipRequestsTable: DynamoDbAsyncTable<EnhancedDocument>
fun Application.configureFriendshipRequestsDatabase()
{
    val configTableName = environment.config
        .property("aws.dynamodb.tables.friendships")
        .getString()

    friendshipRequestsTable = Database.client()
        .table(
            configTableName,
            TableSchema.documentSchemaBuilder()
                .addIndexPartitionKey(TableMetadata.primaryIndexName(),"friendshipId", AttributeValueType.S)
                .build()
        )

    friendshipRequestsTable.describeTable().thenAccept {
        log.info("Using friendship requests table: ${it.table().tableArn()}")
    }
}

fun friendshipRequestsTable() = friendshipRequestsTable
