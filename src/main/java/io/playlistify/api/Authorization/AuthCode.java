package io.playlistify.api.Authorization;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.io.IOException;

public class AuthCode {

    private final SpotifyApi spotifyApi;

    /**
     * The authorization code.
     */
    private String authCode;

    public AuthCode(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    public AuthCode(SpotifyApi spotifyApi, String authCode) {
        this.spotifyApi = spotifyApi;
        setCode(authCode);
    }

    /**
     * Requests an authorization code from Spotify and sets the Access and Refresh tokens for the {@link SpotifyApi} object.
     */
    public void setAccessAndRefreshToken() {
        AuthorizationCodeRequest authorizationCodeRequest = getAuthorizationCodeRequest();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for spotifyApi object
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private AuthorizationCodeRequest getAuthorizationCodeRequest() {
        AuthorizationCodeRequest authorizationCodeRequest =
                spotifyApi.authorizationCode(authCode)
                        .build();

        return authorizationCodeRequest;
    }

    /**
     * Sets the {@link #authCode}.
     */
    public void setCode(String authCode) {
        this.authCode = authCode;
        // maybe this should be somewhere else...
        setAccessAndRefreshToken();
    }

    /**
     * Gets the authorization code.
     *
     * @return {@link #authCode}.
     */
    public String getCode() {
        return this.authCode;
    }
}
