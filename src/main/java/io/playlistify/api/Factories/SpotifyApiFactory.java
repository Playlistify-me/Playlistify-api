package io.playlistify.api.Factories;

import io.github.cdimascio.dotenv.Dotenv;
import io.playlistify.api.Authorization.ClientCredentialsDto;
import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Authorization.TokenDto;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

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

    public static SpotifyApi getSpotifyApiClientCredentials() {
        SpotifyApi spotifyApi = getBasicSpotifyApi();

        ClientCredentialsDto clientCredentialsTokens = SpotifyApiAuthenticator.getClientCredentialsAccessToken(spotifyApi);

        spotifyApi.setAccessToken(clientCredentialsTokens.getAccessToken());

        return spotifyApi;
    }
}
