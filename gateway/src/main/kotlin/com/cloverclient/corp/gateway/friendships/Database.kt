package com.cloverclient.corp.gateway.friendships

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
private lateinit var friendshipsTable: DynamoDbAsyncTable<EnhancedDocument>
fun Application.configureFriendshipsDatabase()
{
    val configTableName = environment.config
        .property("aws.dynamodb.tables.friendships")
        .getString()

    friendshipsTable = Database.client()
        .table(
            configTableName,
            TableSchema.documentSchemaBuilder()
                .addIndexPartitionKey(TableMetadata.primaryIndexName(),"friendshipId", AttributeValueType.S)
                .build()
        )

    friendshipsTable.describeTable().thenAccept {
        log.info("Using friendships table: ${it.table().tableArn()}")
    }
}

fun friendshipsTable() = friendshipsTable
