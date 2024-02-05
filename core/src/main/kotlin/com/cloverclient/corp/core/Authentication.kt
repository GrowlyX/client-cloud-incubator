package com.cloverclient.corp.core

import com.cloverclient.corp.core.authentication.KtorAWSAlbAuthenticationProvider
import com.cloverclient.corp.core.idp.IdpUser
import com.rbinternational.awstools.awsjwtvalidator.AWSAlbUserClaimsTokenValidator
import io.ktor.server.application.*
import io.ktor.server.auth.*

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.configureAuthentication()
{
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val realm = environment.config.property("jwt.realm").getString()

    authentication {
        val tokenValidator = AWSAlbUserClaimsTokenValidator()
        register(KtorAWSAlbAuthenticationProvider(
            name = "account",
            realm = realm,
            tokenValidator = tokenValidator,
            audience = jwtAudience,
            issuer = issuer
        ) {
            IdpUser(it)
        })
    }
}
