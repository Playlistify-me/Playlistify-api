package io.playlistify.api.controllers;

import io.playlistify.api.authorization.SpotifyApiAuthenticator;
import io.playlistify.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


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
}
