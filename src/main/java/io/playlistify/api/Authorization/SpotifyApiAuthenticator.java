package io.playlistify.api.Authorization;

import io.playlistify.api.Factories.SpotifyApiFactory;
import io.playlistify.api.GenerateState;
import lombok.Getter;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;

public class SpotifyApiAuthenticator {
    /**
     * The {@link SpotifyApi} object required for the URI and Tokens.
     */
    private static final SpotifyApi spotifyApi = SpotifyApiFactory.getBasicSpotifyApi();

    /**
     * Generates the {@link URI} from the {@link AuthorizationCodeUriRequest} object.
     * @return {@link URI}.
     */
    public static URI generateAuthCodeUri() {
        return getAuthorizationCodeUriRequest().execute();
    }

    /**
     * Requests and sets the Access and the Refresh tokens in the database.
     *
     * @param authCode The authorization code.
     * This is the code that is returned to the redirect URI after the user has accepted the scopes.
     * @return The access token.
     */
    public static TokenDto getAccessSetRefreshToken(String authCode) {
        // Either return [] with both tokens or keep it like this. Not sure yet.
        // I guess it also needs the user id?
        AuthorizationCodeRequest authorizationCodeRequest = getAuthorizationCodeRequest(authCode);

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            String accessToken = authorizationCodeCredentials.getAccessToken();
            String refreshToken = authorizationCodeCredentials.getRefreshToken();
            return new TokenDto(accessToken, refreshToken);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    /**
     * Gets the {@link AuthorizationCodeRequest} required for the {@link AuthorizationCodeCredentials}.
     * @return
     */
    private static AuthorizationCodeRequest getAuthorizationCodeRequest(String authCode) {
        return spotifyApi.authorizationCode(authCode)
                .build();
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

    private static ClientCredentialsRequest getClientCredentialsRequest(SpotifyApi spotifyApi) {
        return spotifyApi.clientCredentials()
                .build();
    }

    /**
     * Gets the access token and expiry time, required for the {@link ClientCredentials}.
     * @param spotifyApi The {@link SpotifyApi} object required for the {@link ClientCredentialsRequest}.
     * @return {@link ClientCredentialsDto} containing the access token and the time the access token expires.
     */
    public static ClientCredentialsDto getClientCredentialsAccessToken(SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = getClientCredentialsRequest(spotifyApi);

            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            final String accessToken = clientCredentials.getAccessToken();
            final int expiresInSeconds = clientCredentials.getExpiresIn();

            Instant expiresAt = Instant.now().plusSeconds(expiresInSeconds);

            return new ClientCredentialsDto(accessToken, expiresAt);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }
}
