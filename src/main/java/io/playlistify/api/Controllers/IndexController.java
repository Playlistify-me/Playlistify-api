package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        URI authCodeURI = SpotifyApiAuthenticator.generateAuthCodeUri();
        return "uri = " + authCodeURI.toString();
    }

    @GetMapping("/test")
    public RedirectView test() {
        URI authCodeURI = SpotifyApiAuthenticator.generateAuthCodeUri();
        return new RedirectView(authCodeURI.toString());
    }
}
