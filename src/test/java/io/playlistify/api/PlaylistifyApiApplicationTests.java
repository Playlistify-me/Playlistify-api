package io.playlistify.api;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Factories.SpotifyApiFactory;
import io.playlistify.api.Utils.Environment.EnvVariableManager;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

import java.io.IOException;
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

    @Test
    void clientCredentialsDevUserIdTest() {
        final String spotifyDevAccountId = "31htnbwollsrbp7lmf3uvwq3h3pu";
        final String expectedUserId = spotifyDevAccountId;
        String actualUserId = "";

        try {
            final SpotifyApi spotifyApiClientCredentials = SpotifyApiFactory.getSpotifyApiClientCredentials();

            actualUserId = spotifyApiClientCredentials
                    .getUsersProfile(spotifyDevAccountId)
                    .build()
                    .execute()
                    .getId();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            Assertions.fail("Exception occurred: " + e.getMessage());
        }

        Assertions.assertEquals(expectedUserId, actualUserId);
    }

    @Test
    void clientCredentialsGetCurrentUsersIdThrowsUnauthorizedExceptionTest() {
        final String spotifyDevAccountId = "31htnbwollsrbp7lmf3uvwq3h3pu";
        String actualUserId = "";

        try {
            final SpotifyApi spotifyApiClientCredentials = SpotifyApiFactory.getSpotifyApiClientCredentials();

            actualUserId = spotifyApiClientCredentials
                    .getCurrentUsersProfile()
                    .build()
                    .execute()
                    .getId();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            Assertions.assertThrowsExactly(UnauthorizedException.class, () -> {
                throw e;
            });
        }

        Assertions.assertNotEquals(spotifyDevAccountId, actualUserId);
    }
}
