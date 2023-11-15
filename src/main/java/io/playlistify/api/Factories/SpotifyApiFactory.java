package io.playlistify.api.Factories;

import io.github.cdimascio.dotenv.Dotenv;
import io.playlistify.api.Authorization.TokenDto;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

public class SpotifyApiFactory {
    public static SpotifyApi getBasicSpotifyApi() {
        Dotenv dotenv = Dotenv.load();

        final String spotifyClientId = dotenv.get("spotify.client.id");
        final String spotifyClientSecret = dotenv.get("spotify.client.secret");
        final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");

        return new SpotifyApi.Builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifyClientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }

    public static SpotifyApi getSpotifyApiWithTokens(TokenDto tokens) {
        Dotenv dotenv = Dotenv.load();

        final String spotifyClientId = dotenv.get("spotify.client.id");
        final String spotifyClientSecret = dotenv.get("spotify.client.secret");

        return new SpotifyApi.Builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifyClientSecret)
                .setAccessToken(tokens.getAccessToken())
                .setRefreshToken(tokens.getRefreshToken())
                .build();
    }
}
