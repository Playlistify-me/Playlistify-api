package io.playlistify.api.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;


/**
 * @accessToken {@link String} - the access token
 * @expiresAt {@link Instant} - the time stamp from which the access token is invalid
 */
@Getter
@AllArgsConstructor
public class ClientCredentialsDto {
    private final String accessToken;
    private final Instant expiresAt;
}

