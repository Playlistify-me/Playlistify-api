package io.playlistify.api.utils.user;

import io.playlistify.api.factories.SpotifyApiFactory;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

public class UserUtils {

    public static String getCurrentUserId(String accessToken) throws IOException, ParseException, SpotifyWebApiException {
        return SpotifyApiFactory
                .getSpotifyApiWithAccessToken(accessToken)
                .getCurrentUsersProfile()
                .build()
                .execute()
                .getId();
    }

    public static String getCurrentUserId(SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException {
        return spotifyApi
                .getCurrentUsersProfile()
                .build()
                .execute()
                .getId();
    }
}
