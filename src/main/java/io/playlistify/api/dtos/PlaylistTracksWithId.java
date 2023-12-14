package io.playlistify.api.dtos;

import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.util.Objects;

public class PlaylistTracksWithId {
    final String id;
    final PlaylistTrack[] playlistTracks;

    public PlaylistTracksWithId(String id, PlaylistTrack[] playlistTracks) {
        this.id = id;
        this.playlistTracks = Objects.requireNonNull(playlistTracks, "playlistTracks must not be null");
    }

    public PlaylistTrack[] getPlaylistTracks() {
        return this.playlistTracks;
    }

    public String getId() {
        return id;
    }
}
