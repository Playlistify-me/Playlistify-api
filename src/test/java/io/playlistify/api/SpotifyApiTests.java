package io.playlistify.api;

import io.playlistify.api.Factories.SpotifyApiFactory;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

import java.io.IOException;
import java.net.UnknownHostException;

public class SpotifyApiTests {

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
            if (e.getClass() == UnknownHostException.class) {
                Assertions.fail("ERROR WITH HOST: " + e.getMessage());
            }

            Assertions.assertThrowsExactly(UnauthorizedException.class, () -> {
                throw e;
            });
        }

        Assertions.assertNotEquals(spotifyDevAccountId, actualUserId);
    }
}
