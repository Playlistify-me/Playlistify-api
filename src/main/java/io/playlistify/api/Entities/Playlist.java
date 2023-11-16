package io.playlistify.api.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(unique = true)
    public String spotify_uri;

    @ManyToMany()
    @JoinTable(
            name = "TrackInPlaylist",
            joinColumns = @JoinColumn(name = "Playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "Track_id")
    )
    public Set<Track> tracks;
}
