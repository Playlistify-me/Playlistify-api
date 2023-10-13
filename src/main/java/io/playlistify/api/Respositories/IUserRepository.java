package io.playlistify.api.Respositories;

import java.util.UUID;

public interface IUserRepository {
    String getAccessTokenForUserId(UUID userId);
    boolean setAccessTokenForUserId(UUID userId, String accessToken);
    String getRefreshTokenForUserId(UUID userId);
    boolean setRefreshTokenForUserId(UUID userId, String refreshToken);
}
