package io.playlistify.api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(unique = true)
    public String spotify_uri;

    @ManyToMany(mappedBy = "tracks")
    public Set<Playlist> playlists;
}
