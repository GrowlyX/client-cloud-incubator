package com.cloverclient.corp.core.idp

import com.cloverclient.corp.core.authentication.AWSAlbPrincipal
import io.ktor.server.application.*
import io.ktor.server.auth.*
import java.util.*

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
class IdpUser(principal: AWSAlbPrincipal) : AWSAlbPrincipal(principal)
{
    val email = principal["email"]!!.toString()
    val sub = UUID.fromString(principal["sub"]!!.toString())
}

fun ApplicationCall.idpUser() = principal<IdpUser>()!!
