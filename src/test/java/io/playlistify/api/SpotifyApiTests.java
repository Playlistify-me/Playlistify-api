package io.playlistify.api;

import io.playlistify.api.authorization.SpotifyApiAuthenticator;
import io.playlistify.api.authorization.TokenDto;
import io.playlistify.api.factories.SpotifyApiFactory;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

import java.io.IOException;
import java.net.UnknownHostException;

class SpotifyApiTests {

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

    @Test
    void spotifyApiWithSetTokensTest() {
        final String expectedAccessToken = "placeholder_access_token";
        final String expectedRefreshToken = "placeholder_refresh_token";

        final TokenDto tokens = new TokenDto(expectedAccessToken, expectedRefreshToken);

        final SpotifyApi spotifyApi = SpotifyApiFactory.getSpotifyApiWithTokens(tokens);
        final String actualAccessToken = spotifyApi.getAccessToken();
        final String actualRefreshToken = spotifyApi.getRefreshToken();

        Assertions.assertEquals(expectedAccessToken, actualAccessToken);
        Assertions.assertEquals(expectedRefreshToken, actualRefreshToken);
    }

    @Test
    void privateConstructorTest() throws Exception {
        TestUtils.assertPrivateConstructor(SpotifyApiFactory.class);
        TestUtils.assertPrivateConstructor(SpotifyApiAuthenticator.class);
    }
}
