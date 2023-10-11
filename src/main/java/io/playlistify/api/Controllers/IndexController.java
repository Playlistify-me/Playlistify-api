package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;

@RestController
public class IndexController {
    private final URI authCodeUri = SpotifyApiAuthenticator.getAuthCodeUri();

    @GetMapping("/")
    public String index() {
        return "uri = " + authCodeUri.toString();
    }

    @GetMapping("/test")
    public RedirectView test() {
        return new RedirectView(authCodeUri.toString());
    }
}
