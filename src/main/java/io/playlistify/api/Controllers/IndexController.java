package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.AuthSpotifyApi;
import io.playlistify.api.Authorization.AuthUri;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;

@RestController
public class IndexController {

    private static final String clientId = AuthSpotifyApi.getSpotifyClientId();
    private final String secretId = AuthSpotifyApi.getSpotifySecretId();

    private static SpotifyApi authSpotifyApi = AuthSpotifyApi.getSpotifyApi();

    private static URI authCodeUri = new AuthUri(authSpotifyApi).getAuthorizationCodeUri();

    @GetMapping("/")
    public String index() {
        return "uri = " + authCodeUri.toString();
    }

    @GetMapping("/test")
    public RedirectView test() {
        return new RedirectView(authCodeUri.toString());
    }
}
