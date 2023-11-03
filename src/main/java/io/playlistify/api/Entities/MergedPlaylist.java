package io.playlistify.api.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class MergedPlaylist extends Playlist{
    public boolean isMerged;

    @ManyToMany
    @JoinTable(
            name = "PlaylistInMergedPlaylist",
            joinColumns = @jakarta.persistence.JoinColumn(name = "MergedPlaylist_id"),
            inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "Playlist_id")
    )
    public Set<Playlist> includedPlaylists;
}
