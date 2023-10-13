package io.playlistify.api.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "access_token")
    public String accessToken;
    @Column(name = "refresh_token")
    public String refreshToken;
}
