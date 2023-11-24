package io.playlistify.api;

import io.playlistify.api.authorization.SpotifyApiAuthenticator;
import io.playlistify.api.utils.environment.EnvVariableManager;
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
        final String clientId = EnvVariableManager.getSpotifyClientIdValue();
        final String redirectUrl = EnvVariableManager.getSpotifyRedirectUrlValue();

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
