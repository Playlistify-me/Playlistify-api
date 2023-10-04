package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;

@RestController
public class IndexController {

    private static final String clientId = Authorization.getSpotifyClientId();
    private final String secretId = Authorization.getSpotifySecretId();

    private static SpotifyApi authSpotifyApi = Authorization.getSpotifyApi();

    private static URI authCodeUri = Authorization.getAuthorizationCodeUri();

    @GetMapping("/")
    public String index() {
        return "uri = " + authCodeUri.toString();
    }

    @GetMapping("/test")
    public RedirectView test() {
        return new RedirectView(authCodeUri.toString());
    }
}
