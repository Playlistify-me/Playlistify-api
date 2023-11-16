package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.TokenDto;
import io.playlistify.api.Factories.SpotifyApiFactory;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.io.IOException;
import java.util.Arrays;


@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/me")
    public PlaylistSimplified[] getMyPlaylists (@RequestBody TokenDto receivedTokens){
        System.out.println("received");
        logger.info("access = " + receivedTokens.getAccessToken());
        logger.info("refresh = " +  receivedTokens.getRefreshToken());


        SpotifyApi spotifyApi = SpotifyApiFactory.getSpotifyApiWithTokens(receivedTokens);
        logger.info("receivedToken accessToken = " + receivedTokens.getAccessToken());
        PlaylistSimplified[] a = null;
        try {
            a = spotifyApi.getListOfCurrentUsersPlaylists().build().execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e){
            logger.info("exception: " + e.getMessage());
        }

        if (a == null) {
            logger.info("a  is null ");
            return a;}

        int counter = 0;
        for (PlaylistSimplified playlistSimplified
             : a) {
            counter++;
            logger.info("current a [" + counter + "] = " + playlistSimplified.toString());
        }

        return a;
    }
}
