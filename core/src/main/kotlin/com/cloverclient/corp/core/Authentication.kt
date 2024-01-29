package com.cloverclient.corp.core

import com.auth0.jwk.JwkProviderBuilder
import com.cloverclient.corp.core.utilities.AuthenticationAuthHeaderExtractUtilities
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.concurrent.TimeUnit

/**
 * @author GrowlyX
 * @since 1/28/2024
 */
fun Application.configureAuthentication()
{
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val realm = environment.config.property("jwt.realm").getString()

    val jwkProvider = JwkProviderBuilder(issuer)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    authentication {
        jwt("account") {
            this.realm = realm

            verifier(jwkProvider, issuer) {
                acceptLeeway(3)
            }

            authHeader { call ->
                val jwtToken = call.request.headers["X-AMZN-OIDC-DATA"]
                    ?: return@authHeader null

                AuthenticationAuthHeaderExtractUtilities.INSTANCE
                    .extractAuthHeaderFrom(jwtToken)
            }

            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience))
                    JWTPrincipal(credential.payload)
                else null
            }
        }
    }
}
