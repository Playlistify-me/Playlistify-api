package io.playlistify.api.Factories;

import io.github.cdimascio.dotenv.Dotenv;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

public class SpotifyApiFactory {
    public static SpotifyApi getBasicSpotifyApi() {
        Dotenv dotenv = Dotenv.load();

        final String spotifyClientId = dotenv.get("spotify.client.id");
        final String spotifySecretId = dotenv.get("spotify.client.secret");
        final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifySecretId)
                .setRedirectUri(redirectUri)
                .build();

        return spotifyApi;
    }

    public static SpotifyApi getSpotifyApiWithAccessToken(String accessToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        return spotifyApi;
    }
}
