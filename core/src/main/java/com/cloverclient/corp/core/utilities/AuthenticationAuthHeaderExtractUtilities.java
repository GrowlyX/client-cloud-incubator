package com.cloverclient.corp.core.utilities;

import io.ktor.http.auth.HttpAuthHeader;
import io.ktor.http.auth.HttpAuthHeaderKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A small utility to proxy {@link HttpAuthHeader}'s parseAuthorizationHeader
 * method as it's not visible to us in Kotlin.
 *
 * @author GrowlyX
 * @since 1/28/2024
 */
public enum AuthenticationAuthHeaderExtractUtilities
{
    INSTANCE;

    public @Nullable HttpAuthHeader extractAuthHeaderFrom(@NotNull String header)
    {
        return HttpAuthHeaderKt.parseAuthorizationHeader(header);
    }
}
