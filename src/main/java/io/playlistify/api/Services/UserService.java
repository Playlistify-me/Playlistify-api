package io.playlistify.api.Services;

import io.playlistify.api.Respositories.IUserRepository;

import java.util.UUID;

public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getAccessTokenForUserId(UUID userId) {
        return userRepository.getAccessTokenForUserId(userId);
    }

    @Override
    public boolean setAccessTokenForUserId(UUID userId, String accessToken) {
        return userRepository.setAccessTokenForUserId(userId, accessToken);
    }

    @Override
    public String getRefreshTokenForUserId(UUID userId) {
        return userRepository.getRefreshTokenForUserId(userId);
    }

    @Override
    public boolean setRefreshTokenForUserId(UUID userId, String refreshToken) {
        return userRepository.setRefreshTokenForUserId(userId, refreshToken);
    }
}
