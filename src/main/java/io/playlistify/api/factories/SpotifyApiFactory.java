package io.playlistify.api.factories;

import io.playlistify.api.authorization.ClientCredentialsDto;
import io.playlistify.api.authorization.SpotifyApiAuthenticator;
import io.playlistify.api.authorization.TokenDto;
import io.playlistify.api.utils.environment.EnvVariableManager;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.net.URI;

public class SpotifyApiFactory {
    static final String CLIENT_ID = EnvVariableManager.getSpotifyClientIdValue();
    static final String CLIENT_SECRET = EnvVariableManager.getSpotifyClientSecretValue();
    static final String REDIRECT_URL = EnvVariableManager.getSpotifyRedirectUrlValue();

    private SpotifyApiFactory() {
    }

    public static SpotifyApi getBasicSpotifyApi() {
        final URI redirectUri = SpotifyHttpManager.makeUri(REDIRECT_URL);

        return new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(redirectUri)
                .build();
    }

    public static SpotifyApi getSpotifyApiWithTokens(TokenDto tokens) {
        return new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setAccessToken(tokens.getAccessToken())
                .setRefreshToken(tokens.getRefreshToken())
                .build();
    }

    public static SpotifyApi getSpotifyApiClientCredentials() throws IOException, ParseException, SpotifyWebApiException {
        final SpotifyApi spotifyApi = getBasicSpotifyApi();

        final ClientCredentialsDto clientCredentialsTokens = SpotifyApiAuthenticator.getClientCredentialsAccessToken(spotifyApi);

        spotifyApi.setAccessToken(clientCredentialsTokens.getAccessToken());

        return spotifyApi;
    }
}
