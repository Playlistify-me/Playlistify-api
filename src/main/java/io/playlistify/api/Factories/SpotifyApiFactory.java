package io.playlistify.api.Factories;

import io.github.cdimascio.dotenv.Dotenv;
import io.playlistify.api.Authorization.ClientCredentialsDto;
import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Authorization.TokenDto;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.net.URI;

public class SpotifyApiFactory {
    static final Dotenv dotenv = Dotenv.load();

    public static SpotifyApi getBasicSpotifyApi() {
        final String spotifyClientId = dotenv.get("spotify.client.id");
        final String spotifyClientSecret = dotenv.get("spotify.client.secret");
        final String spotifyRedirectUrl = dotenv.get("spotify.redirect.url");

        final URI redirectUri = SpotifyHttpManager.makeUri(spotifyRedirectUrl);

        return new SpotifyApi.Builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifyClientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }

    public static SpotifyApi getSpotifyApiWithTokens(TokenDto tokens) {
        final String spotifyClientId = dotenv.get("spotify.client.id");
        final String spotifyClientSecret = dotenv.get("spotify.client.secret");

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
