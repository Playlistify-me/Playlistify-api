package io.playlistify.api.Authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthCodeRequestDto {
    private String authCode;
    private String state;
}
