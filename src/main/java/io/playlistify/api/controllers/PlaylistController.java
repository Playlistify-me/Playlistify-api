package io.playlistify.api.controllers;

import io.playlistify.api.authorization.TokenDto;
import io.playlistify.api.dtos.PlaylistTracksWithId;
import io.playlistify.api.factories.SpotifyApiFactory;
import io.playlistify.api.models.PlaylistIdUriModel;
import io.playlistify.api.utils.playlist.PlaylistUtils;
import io.playlistify.api.utils.track.TrackUtils;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.io.IOException;


@RestController
@RequestMapping("/playlist")
public class PlaylistController {
private static final String playlistifyDevAccountId = "31htnbwollsrbp7lmf3uvwq3h3pu";
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistController.class);

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/me")
    public PlaylistSimplified[] getMyPlaylists(@RequestBody TokenDto receivedTokens) {
        LOGGER.info("received get request for my playlists");

        SpotifyApi spotifyApi = SpotifyApiFactory.getSpotifyApiWithTokens(receivedTokens);
        PlaylistSimplified[] playlistsSimplified = null;
        try {
            playlistsSimplified = spotifyApi.getListOfCurrentUsersPlaylists().build().execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: {}", e.getMessage());
        }

        if (playlistsSimplified == null) {
            LOGGER.info("playlistsSimplified is null");
            return null;
        }

        int counter = 0;
        for (PlaylistSimplified playlistSimplified
                : playlistsSimplified) {
            counter++;
            LOGGER.info("current PS [" + counter + "] = " + playlistSimplified.toString());
        }

        return playlistsSimplified;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/metest")
    public PlaylistSimplified[] getPlaylistifyDevPlaylistsTest() {
        LOGGER.info("received");

        PlaylistSimplified[] playlists = null;

        try {
            SpotifyApi spotifyApi = SpotifyApiFactory.getSpotifyApiClientCredentials();

            playlists = spotifyApi.getListOfUsersPlaylists(playlistifyDevAccountId).build().execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: {}", e.getMessage());
        }

        return playlists;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getTracks/{playlistIds}")
    @ResponseBody
    public PlaylistTracksWithId[] getTracksFromPlaylist(@PathVariable String[] playlistIds) {
        //TODO: for now only dev account so still has to include accessToken some day

        LOGGER.info("received get request for playlistTracksWithId[] with playlist ids: {}", (Object) playlistIds);

        PlaylistIdUriModel[] playlistIdUriModels = PlaylistUtils.getPlaylistModelsFromPlaylistUrisOrIds(playlistIds);
        PlaylistTrack[] playlistTracks = null;
        PlaylistTracksWithId[] playlistTracksWithId = new PlaylistTracksWithId[playlistIds.length];

        try {
            SpotifyApi spotifyApi = SpotifyApiFactory.getSpotifyApiClientCredentials();
            Playlist[] playlists = PlaylistUtils.getPlaylistsFromPlaylistIdUrlModels(playlistIdUriModels, spotifyApi);
            playlistTracks = TrackUtils.getPlaylistTracksFromPlaylists(playlists);

            for (int i = 0; i < playlistIdUriModels.length; i++) {
                String trackId = playlistIdUriModels[i].getId();
                playlistTracksWithId[i] = new PlaylistTracksWithId(trackId, playlistTracks);
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: {}", e.getMessage());
        }

        return playlistTracksWithId;
    }
}
