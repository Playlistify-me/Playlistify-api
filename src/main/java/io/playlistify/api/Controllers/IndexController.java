package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Entities.User;
import io.playlistify.api.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;
import java.util.List;

@RestController
public class IndexController {

    private final UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        URI authCodeURI = SpotifyApiAuthenticator.generateAuthCodeUri();
        return "uri = " + authCodeURI.toString();
    }

    @GetMapping("/users")
    public List<User> GetAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/test")
    public RedirectView test() {
        URI authCodeURI = SpotifyApiAuthenticator.generateAuthCodeUri();
        return new RedirectView(authCodeURI.toString());
    }
}
