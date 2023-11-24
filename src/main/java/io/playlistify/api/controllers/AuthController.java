package io.playlistify.api.controllers;

import io.playlistify.api.authorization.AuthCodeRequestDto;
import io.playlistify.api.authorization.SpotifyApiAuthenticator;
import io.playlistify.api.authorization.TokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("urigenerator")
    public ResponseEntity<URI> GetSpotifyAuthCodeURI() {
        URI authCodeURI = SpotifyApiAuthenticator.generateAuthCodeUri();
        return ResponseEntity.ok(authCodeURI);
    }

    @PostMapping("authcodegrant")
    public ResponseEntity AuthCodeRequest(@RequestBody() AuthCodeRequestDto authCodeRequestDto) {
        TokenDto tokens = SpotifyApiAuthenticator.getTokenDto(authCodeRequestDto.getAuthCode());

        return ResponseEntity.ok("ok");
    }
}
