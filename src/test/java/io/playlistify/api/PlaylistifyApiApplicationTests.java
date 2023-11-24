package io.playlistify.api;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Factories.SpotifyApiFactory;
import io.playlistify.api.Utils.Environment.EnvVariableManager;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class PlaylistifyApiApplicationTests {
    @Test
    void authUriTest() {
        final String clientId = EnvVariableManager.getSpotifyClientId();
        final String redirectUrl = EnvVariableManager.getSpotifyRedirectUrl();

        // encoding url because it's encoded from generateAuthCodeUri
        final String redirectUrlEncoded = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);

        final URI expectedAuthUri = URI.create(
                "https://accounts.spotify.com:443/authorize?client_id=" +
                        clientId +
                        "&response_type=code&redirect_uri=" +
                        redirectUrlEncoded
        );

        final URI actualAuthUri = SpotifyApiAuthenticator.generateAuthCodeUri();

        Assertions.assertEquals(expectedAuthUri, actualAuthUri);
    }
}
