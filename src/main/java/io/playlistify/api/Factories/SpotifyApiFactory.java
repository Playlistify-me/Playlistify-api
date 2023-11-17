package io.playlistify.api.Factories;

import io.github.cdimascio.dotenv.Dotenv;
import io.playlistify.api.Authorization.ClientCredentialsDto;
import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Authorization.TokenDto;
import io.playlistify.api.Utils.EnvVariableManager;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.net.URI;

public class SpotifyApiFactory {
    static final String spotifyClientId = EnvVariableManager.getSpotifyClientId();
    static final String spotifyClientSecret = EnvVariableManager.getSpotifyClientSecret();
    static final String spotifyRedirectUrl = EnvVariableManager.getSpotifyRedirectUrl();

    public static SpotifyApi getBasicSpotifyApi() {
        final URI redirectUri = SpotifyHttpManager.makeUri(spotifyRedirectUrl);

        return new SpotifyApi.Builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifyClientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }

    public static SpotifyApi getSpotifyApiWithTokens(TokenDto tokens) {
        return new SpotifyApi.Builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifyClientSecret)
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
