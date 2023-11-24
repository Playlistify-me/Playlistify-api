package io.playlistify.api.controllers;

import io.playlistify.api.authorization.TokenDto;
import io.playlistify.api.factories.SpotifyApiFactory;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.io.IOException;


@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistController.class);

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/me")
    public PlaylistSimplified[] getMyPlaylists(@RequestBody TokenDto receivedTokens) {
        LOGGER.info("received");

        SpotifyApi spotifyApi = SpotifyApiFactory.getSpotifyApiWithTokens(receivedTokens);
        PlaylistSimplified[] a = null;
        try {
            a = spotifyApi.getListOfCurrentUsersPlaylists().build().execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: {}", e.getMessage());
        }

        if (a == null) {
            LOGGER.info("a is null");
            return a;
        }

        int counter = 0;
        for (PlaylistSimplified playlistSimplified
                : a) {
            counter++;
            LOGGER.info("current a [" + counter + "] = " + playlistSimplified.toString());
        }

        return a;
    }
}
