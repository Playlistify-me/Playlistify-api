package io.playlistify.api.utils.track;

import io.playlistify.api.models.TrackIdUriModel;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class TrackUtils {

    private TrackUtils() {
    }


    public static PlaylistTrack[] getPlaylistTracksFromPlaylist(Playlist playlist) {
        return playlist.getTracks().getItems();
    }

    public static PlaylistTrack[] getPlaylistTracksFromPlaylists(Playlist[] playlists) {
        int totalAmountOfTracks = getTotalAmountOfTracks(playlists);
        int playlistTracksPosition = 0;

        PlaylistTrack[] playlistTracks = new PlaylistTrack[totalAmountOfTracks];

        for (Playlist playlist : playlists) {
            int amountOfTracksInPlaylist = getAmountOfTracks(playlist);

            for (int i = 0; i < amountOfTracksInPlaylist; i++) {
                playlistTracks[playlistTracksPosition] = getPlaylistTracksFromPlaylist(playlist)[i];
                playlistTracksPosition++;
            }
        }

        return playlistTracks;
    }

    public static int getAmountOfTracks(Playlist playlist) {
        return playlist.getTracks().getTotal();
    }

    public static int getTotalAmountOfTracks(Playlist[] playlists) {
        int totalAmountOfTracks = 0;

        for (Playlist playlist : playlists) {
            totalAmountOfTracks += getAmountOfTracks(playlist);
        }

        return totalAmountOfTracks;
    }

    public static int[] getAmountOfTracksPerPlaylist(Playlist[] playlists) {
        int[] amountOfTracksPerPlaylist = new int[playlists.length];

        for (int i = 0; i < playlists.length; i++) {
            amountOfTracksPerPlaylist[i] = getAmountOfTracks(playlists[i]);
        }

        return amountOfTracksPerPlaylist;
    }

    public static TrackIdUriModel getTrackModelFromPlaylistTrack(PlaylistTrack playlistTrack) {
        String playlistTrackUri = playlistTrack.getTrack().getUri();
        return new TrackIdUriModel(playlistTrackUri);
    }

    public static TrackIdUriModel[] getTrackModelsFromPlaylistTracks(PlaylistTrack[] playlistTracks) {
        TrackIdUriModel[] trackIdUriModels = new TrackIdUriModel[playlistTracks.length];

        for (int i = 0; i < playlistTracks.length; i++) {
            trackIdUriModels[i] = getTrackModelFromPlaylistTrack(playlistTracks[i]);
        }

        return trackIdUriModels;
    }

    // maybe uri utils? lol vvv
    public static String[] getTrackUrisFromPlaylist(Playlist playlist) {
        int amountOfTracks = getAmountOfTracks(playlist);
        String[] trackUris = new String[amountOfTracks];

        for (int i = 0; i < amountOfTracks; i++) {
            // lmao (????)
            trackUris[i] = getTrackUriFromTrackModel(
                    getTrackModelFromPlaylistTrack(
                            getPlaylistTracksFromPlaylist(
                                    playlist
                            )[i]
                    )
            );
        }

        return trackUris;
    }

    public static String[] getTrackUrisFromPlaylists(Playlist[] playlists) {
        int totalAmountOfTracks = getTotalAmountOfTracks(playlists);

        String[] trackUris = new String[totalAmountOfTracks];

        for (Playlist playlist : playlists) {
            trackUris = getTrackUrisFromPlaylist(playlist);
        }

        return trackUris;
    }

    public static String[] getTrackUrisFromTrackModels(TrackIdUriModel[] trackIdUriModels) {
        String[] trackUris = new String[trackIdUriModels.length];

        for (int i = 0; i < trackIdUriModels.length; i++) {
            trackUris[i] = trackIdUriModels[i].getUri();
            // trackUris[i] = getTrackUriFromTrackModel(trackIdUriModels[i]);
        }

        return trackUris;
    }

    // unnecessary I guess?
    public static String getTrackUriFromTrackModel(TrackIdUriModel trackIdUriModel) {
        return trackIdUriModel.getUri();
    }
}
