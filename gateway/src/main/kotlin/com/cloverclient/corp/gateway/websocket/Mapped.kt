package com.cloverclient.corp.gateway.websocket

import org.glassfish.hk2.api.Metadata
import javax.inject.Qualifier

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
@Qualifier
@Target(AnnotationTarget.TYPE)
annotation class Mapped(
    @get:Metadata("code")
    val value: Int
)
