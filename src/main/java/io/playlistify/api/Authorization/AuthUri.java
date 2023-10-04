package io.playlistify.api.Authorization;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

public class AuthUri {
    private final SpotifyApi spotifyApi;

    /**
     * The authorization code {@link URI}.
     */
    private URI authUri;

    public AuthUri(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
        setAuthorizationCodeUri();
    }

    private AuthorizationCodeUriRequest getAuthorizationCodeUriRequest() {
        return spotifyApi.authorizationCodeUri()
                //.state(#GenerateRandomString() random string here <-)
                .build();
    }

    /**
     * Generates a URI to request an authorization code from Spotify.
     */
    private void setAuthorizationCodeUri() {
        authUri = getAuthorizationCodeUriRequest().execute();
    }

    /**
     * Gets the authorization code URI.
     *
     * @return {@link #authUri}.
     */
    public URI getAuthorizationCodeUri() {
        return authUri;
    }
}