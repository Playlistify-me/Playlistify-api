package io.playlistify.api.Respositories;

import java.util.UUID;

public class UserRepository implements IUserRepository {
    @Override
    public String getAccessTokenForUserId(UUID userId) {
        return "Placeholder Access Token";
    }

    @Override
    public boolean setAccessTokenForUserId(UUID userId, String accessToken) {
        return false;
    }

    @Override
    public String getRefreshTokenForUserId(UUID userId) {
        return "Placeholder Refresh Token";
    }

    @Override
    public boolean setRefreshTokenForUserId(UUID userId, String refreshToken) {
        return false;
    }
}
