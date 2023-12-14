package io.playlistify.api.utils.playlist;

import io.playlistify.api.models.ModelType;
import io.playlistify.api.models.PlaylistIdUriModel;
import io.playlistify.api.models.TrackIdUriModel;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

import java.io.IOException;
import java.util.Arrays;

public class PlaylistUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistUtils.class);
    private static final ModelType playlistModelType = ModelType.PLAYLIST;

    private PlaylistUtils() {
    }


    public static PlaylistIdUriModel[] getPlaylistModelsFromPlaylists(Playlist[] playlists) {
        PlaylistIdUriModel[] playlistIdUriModels = new PlaylistIdUriModel[playlists.length];

        for (int i = 0; i < playlists.length; i++) {
            playlistIdUriModels[i] = new PlaylistIdUriModel(playlists[i].getUri());
        }

        return playlistIdUriModels;
    }

    public static PlaylistIdUriModel[] getPlaylistModelsFromPlaylistUrisOrIds(String[] playlistUrisOrIds) {
        LOGGER.info("playlistUrisOrIds: {}", Arrays.toString(playlistUrisOrIds));
        PlaylistIdUriModel[] playlistIdUriModels = new PlaylistIdUriModel[playlistUrisOrIds.length];

        for (int i = 0; i < playlistUrisOrIds.length; i++) {
            playlistIdUriModels[i] = new PlaylistIdUriModel(playlistUrisOrIds[i]);
        }

        return playlistIdUriModels;
    }

    public static String[] getPlaylistIdsFromPlaylistUris(String[] playlistUris) {
        String[] playlistIds = new String[playlistUris.length];

        for (int i = 0; i < playlistUris.length; i++) {
            playlistIds[i] = playlistUris[i].substring(playlistModelType.getUriPrefixLength());
        }

        return playlistIds;
    }


    // should this throw exceptions?
    public static Playlist[] getPlaylistsFromPlaylistIds(String[] playlistIds, SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException {
        Playlist[] playlists = new Playlist[playlistIds.length];

        for (int i = 0; i < playlistIds.length; i++) {
            playlists[i] = spotifyApi.getPlaylist(playlistIds[i]).build().execute();
        }

        return playlists;
    }

    public static Playlist getPlaylistFromPlaylistIdUrlModel(PlaylistIdUriModel playlistIdUriModel, SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException {
        String playlistId = playlistIdUriModel.getId();

        return spotifyApi.getPlaylist(playlistId).build().execute();
    }

    public static Playlist[] getPlaylistsFromPlaylistIdUrlModels(PlaylistIdUriModel[] playlistIdUriModels, SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException {
        Playlist[] playlists = new Playlist[playlistIdUriModels.length];

        for (int i = 0; i < playlistIdUriModels.length; i++) {
            playlists[i] = getPlaylistFromPlaylistIdUrlModel(playlistIdUriModels[i], spotifyApi);
        }

        return playlists;
    }

    // rethrow probably wrong anyways...
    public static Playlist createNewPlaylist(String currentUserId, String newPlaylistName, SpotifyApi spotifyApi) throws IOException, ParseException, SpotifyWebApiException {
        try {
            return spotifyApi.createPlaylist(currentUserId, newPlaylistName).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: createNewPlaylist {}", e.getMessage());
            throw e;
        }
    }

    public static boolean addItemsToPlaylist(Playlist newPlaylist, TrackIdUriModel[] trackIdUriModels, SpotifyApi spotifyApi) {
        try {
             String[] trackUrisString = new String[trackIdUriModels.length];

            for (int i = 0; i < trackIdUriModels.length; i++) {
                trackUrisString[i] = trackIdUriModels[i].getUri();
            }

            spotifyApi.addItemsToPlaylist(newPlaylist.getId(), trackUrisString).build().execute();

            return true;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: addItemsToPlaylist {}", e.getMessage());
            return false;
        }
    }

    public static boolean addItemsToPlaylist(Playlist newPlaylist, String[] trackUris, SpotifyApi spotifyApi) {
        try {
            spotifyApi.addItemsToPlaylist(newPlaylist.getId(), trackUris).build().execute();

            return true;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("exception: addItemsToPlaylist {}", e.getMessage());
            return false;
        }
    }
}
