package io.playlistify.api;

import io.github.cdimascio.dotenv.Dotenv;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URI;

public final class Authorization {
    public static Dotenv dotenv = Dotenv.load();
    private static final String spotifyClientId = dotenv.get("spotify.client.id");

    private static final String spotifySecretId = dotenv.get("spotify.client.secret");

    private static final String responseType = "code";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");
    private static String code = "";
    private String state = "";
    private static final int generatedStringLength = 35;

    private static SpotifyApi spotifyApi = generateSpotifyApi();

/*    private static AuthorizationCodeRequest authorizationCodeRequest =
            spotifyApi.authorizationCode(code)
                    .build();*/

    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest =
            spotifyApi.authorizationCodeUri()
                    //.state(#GenerateRandomString() random string hier dus <-)
                    .build();

    public Authorization() {
        state = new GenerateRandomString().generateString(generatedStringLength);
        final URI uri = getAuthorizationCodeUri();
    }

    /**
     * Generates a URI to request an authorization code from Spotify.
     *
     * @return The URI to request an authorization code from Spotify.
     */
    public static URI getAuthorizationCodeUri() {
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());

        return uri;
    }

    /**
     * Generates a SpotifyApi object with the client id, client secret, and redirect uri.
     *
     * @return {@link SpotifyApi} object with the client id, client secret, and redirect uri.
     */
    public static SpotifyApi generateSpotifyApi() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifySecretId)
                .setRedirectUri(redirectUri)
                .build();

        return spotifyApi;
    }

    /**
     * Requests an authorization code from Spotify and sets the Access and Refresh tokens for the {@link SpotifyApi} object.
     */
    public static void requestAuthorizationCode() {
        AuthorizationCodeRequest authorizationCodeRequest =
                spotifyApi.authorizationCode(code)
                        .build();

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

    /**
     * Sets the authorization code.
     */
    public static void setCode(String codeToSet) {
        code = codeToSet;
    }

    /**
     * Gets the {@link SpotifyApi} object.
     *
     * @return The {@link SpotifyApi} object.
     */
    public static SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }

    /**
     * Gets the client id for the Spotify application.
     *
     * @return The Client Id of the {@link SpotifyApi} object.
     */
    public static String getSpotifyClientId() {
        return spotifyClientId;
    }

    /**
     * Gets the client secret for the Spotify application.
     *
     * @return The Client Secret of the {@link SpotifyApi} object.
     */
    public static String getSpotifySecretId() {
        return spotifySecretId;
    }

    /**
     * Gets the authorization code.
     *
     * @return The authorization code.
     */
    public static String getCode() {
        return code;
    }
}
