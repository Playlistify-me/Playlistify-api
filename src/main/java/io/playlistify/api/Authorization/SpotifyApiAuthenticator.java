package io.playlistify.api.Authorization;

import io.playlistify.api.Factories.SpotifyApiFactory;
import io.playlistify.api.GenerateState;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;

public class SpotifyApiAuthenticator {
    /**
     * The {@link SpotifyApi} object required for the URI and Tokens.
     */
    private static final SpotifyApi spotifyApi = SpotifyApiFactory.getBasicSpotifyApi();

    /**
     * The authorization code {@link URI}.
     */
    private static URI authCodeUri = generateAuthCodeUri();

    /**
     * Gets the {@link AuthorizationCodeRequest} required for the {@link AuthorizationCodeCredentials}.
     * @return
     */
    private static AuthorizationCodeRequest getAuthorizationCodeRequest(String authCode) {
        return spotifyApi.authorizationCode(authCode)
                .build();
    }

    /**
     * Requests and sets the Access and the Refresh tokens in the database.
     *
     * @param authCode The authorization code.
     * This is the code that is returned to the redirect URI after the user has accepted the scopes.
     * @return The access token.
     */
    public static String getAccessSetRefreshToken(String authCode) {
        // Either return [] with both tokens or keep it like this. Not sure yet.
        // I guess it also needs the user id?
        AuthorizationCodeRequest authorizationCodeRequest = getAuthorizationCodeRequest(authCode);

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            authorizationCodeCredentials.getRefreshToken();
            // TODO:
            //  Set the refresh token in the database.
            //  This is done by calling the UserService.setRefreshTokenForUserId(UUID userId, String refreshToken) method.

            return authorizationCodeCredentials.getAccessToken();
            // TODO:
            //  Set the access token in the database.
            //  This is done by calling the UserService.setAccessTokenForUserId(UUID userId, String accessToken) method.
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    /**
     * Generates the {@link #authCodeUri} from the {@link AuthorizationCodeUriRequest} object.
     * @return {@link #authCodeUri}.
     */
    private static URI generateAuthCodeUri() {
        return getAuthorizationCodeUriRequest().execute();
    }

    /**
     * Gets the {@link AuthorizationCodeUriRequest} required for the {@link #getAccessSetRefreshToken(String) authCode}.
     * @return {@link AuthorizationCodeUriRequest}
     */
    private static AuthorizationCodeUriRequest getAuthorizationCodeUriRequest() {
        //final int generatedStringLength = 35;
        return spotifyApi.authorizationCodeUri()
                //.state(GenerateState.generateString(generatedStringLength);)
                //.scope() scope here
                .build();
    }

    /**
     * Gets the {@link #authCodeUri}.
     *
     * @return {@link #authCodeUri}.
     */
    public static URI getAuthCodeUri() {
        return authCodeUri;
    }
}
