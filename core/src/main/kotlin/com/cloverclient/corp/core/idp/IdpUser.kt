package com.cloverclient.corp.core.idp

import com.cloverclient.corp.core.authentication.AWSAlbPrincipal
import io.ktor.server.application.*
import io.ktor.server.auth.*

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
class IdpUser(principal: AWSAlbPrincipal) : AWSAlbPrincipal(principal)
{

}

fun ApplicationCall.idpUser() = principal<IdpUser>()!!
