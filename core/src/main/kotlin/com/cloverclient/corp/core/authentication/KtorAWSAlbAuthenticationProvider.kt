package com.cloverclient.corp.core.authentication

import com.rbinternational.awstools.awsjwtvalidator.AWSAlbUserClaimsTokenValidator
import io.ktor.http.auth.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

/**
 * @author GrowlyX
 * @since 2/4/2024
 */
const val AmznOidcAuthHeader = "X-Amzn-Oidc-Data"

class KtorAWSAlbAuthenticationProvider(
    name: String,
    private val realm: String,
    private val tokenValidator: AWSAlbUserClaimsTokenValidator,
    private val audience: String,
    private val issuer: String
) : AuthenticationProvider(object : Config(name){})
{
    override suspend fun onAuthenticate(context: AuthenticationContext)
    {
        suspend fun unauthorized() = context.call.respond(
            UnauthorizedResponse(
                HttpAuthHeader.Parameterized(
                    "Bearer",
                    mapOf(HttpAuthHeader.Parameters.Realm to realm)
                )
            )
        )

        val jwtToken = context.call.request.headers[AmznOidcAuthHeader]
            ?: return unauthorized()

        val validateTokensFor = kotlin
            .runCatching { tokenValidator.validateToken(jwtToken) }
            .getOrNull()
            ?: return unauthorized()

        if (
            validateTokensFor.header["client"] != audience ||
            validateTokensFor.body.issuer != issuer
        )
        {
            return unauthorized()
        }

        context.principal(
            name,
            AWSAlbPrincipal(claims = validateTokensFor.body)
        )
    }
}
