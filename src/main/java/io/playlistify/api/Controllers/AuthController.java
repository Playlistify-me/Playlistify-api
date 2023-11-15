package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.AuthCodeRequestDto;
import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Authorization.TokenDto;
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
    public ResponseEntity<?> AuthCodeRequest(@RequestBody() AuthCodeRequestDto authCodeRequestDto) {
        TokenDto tokens = SpotifyApiAuthenticator.getAccessSetRefreshToken(authCodeRequestDto.getAuthCode());
        return ResponseEntity.ok(tokens);
    }
}
