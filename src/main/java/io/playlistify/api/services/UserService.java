package io.playlistify.api.services;

import io.playlistify.api.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User findById(UUID id);

    List<User> findAll();

    User save(User user);

    void deleteById(UUID id);

    String getAccessTokenForUserId(UUID userId);
}
