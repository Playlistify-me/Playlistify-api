package io.playlistify.api.controllers;

import io.playlistify.api.authorization.TokenDto;
import io.playlistify.api.dtos.MergeNewPlaylistDto;
import io.playlistify.api.factories.SpotifyApiFactory;
import io.playlistify.api.models.PlaylistIdUriModel;
import io.playlistify.api.models.TrackIdUriModel;
import io.playlistify.api.utils.playlist.PlaylistUtils;
import io.playlistify.api.utils.track.TrackUtils;
import io.playlistify.api.utils.user.UserUtils;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.io.IOException;
import java.util.Arrays;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/merge")
public class MergeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MergeController.class);

    @PostMapping("/playlists")
    public void mergePlaylists(@RequestBody MergeNewPlaylistDto mergeNewPlaylistDto) {
        String[] playlistIds = mergeNewPlaylistDto.playlistIds();
        String newPlaylistName = mergeNewPlaylistDto.newPlaylistName();
        String accessToken = mergeNewPlaylistDto.accessToken();

        LOGGER.info("received info: playlistUris 1 = {}", playlistIds[0]);
        LOGGER.info("received info: newPlaylistName = {}", newPlaylistName);
        LOGGER.info("received info: accessToken = {}", accessToken);

        PlaylistIdUriModel[] playlistIdUriModels = PlaylistUtils.getPlaylistModelsFromPlaylistUrisOrIds(playlistIds);
        LOGGER.info("first model uri: {}", playlistIdUriModels[0].getUri());

        Playlist[] playlists = null;
        String currentUserId = null;
        Playlist newPlaylist = null;
        PlaylistTrack[] newPlaylistTracks;

        String refreshTokenPlaceholder = "test";
        TokenDto tokens = new TokenDto(accessToken, refreshTokenPlaceholder);

        SpotifyApi spotifyApi = SpotifyApiFactory.getSpotifyApiWithTokens(tokens);


        //get user id
        try {
            currentUserId = UserUtils.getCurrentUserId(spotifyApi);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: userId {}", e.getMessage());
            LOGGER.error("exception: userId ts: {}", e.toString());
            return;
        }

        try {
            playlists = PlaylistUtils.getPlaylistFromPlaylistIdUrlModels(playlistIdUriModels, spotifyApi);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: playlists {}", e.getMessage());
        }

        //get tracks from playlists
        newPlaylistTracks = TrackUtils.getPlaylistTracksFromPlaylists(playlists);


        //create new playlist
        try {
            newPlaylist = PlaylistUtils.createNewPlaylist(currentUserId, newPlaylistName, spotifyApi);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: newPlaylist {}", e.getMessage());
        }

        TrackIdUriModel[] trackIdUriModels = TrackUtils.getTrackModelsFromPlaylistTracks(newPlaylistTracks);

        if (newPlaylist != null) {
            LOGGER.info("new playlist name = {}", newPlaylist.getName());
        }

        //add tracks to new playlist
        PlaylistUtils.addItemsToPlaylist(newPlaylist, trackIdUriModels, spotifyApi);
    }
}
