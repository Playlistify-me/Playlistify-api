package io.playlistify.api.Authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @AllArgsConstructor
public class TokenDto {
    private final String AccessToken;
    private final String RefreshToken;
}