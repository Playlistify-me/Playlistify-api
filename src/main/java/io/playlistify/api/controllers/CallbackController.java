package io.playlistify.api.controllers;

import io.playlistify.api.authorization.SpotifyApiAuthenticator;
import io.playlistify.api.authorization.TokenDto;
import io.playlistify.api.factories.SpotifyApiFactory;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.io.IOException;

@RestController
@RequestMapping("/callback")
public class CallbackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackController.class);

    @GetMapping("")
    public ResponseEntity<String> callback(@RequestParam String code) {
        LOGGER.info("code = " + code);

        TokenDto tokens = SpotifyApiAuthenticator.getTokenDto(code);
        if (tokens == null) {
            LOGGER.error("tokens is null");
            return ResponseEntity.badRequest().body("bad request");
        }

        SpotifyApi spotifyApiWithAccessToken = SpotifyApiFactory.getSpotifyApiWithTokens(tokens);


        try {
            User currentUser = spotifyApiWithAccessToken.getCurrentUsersProfile().build().execute();
            LOGGER.info("user name = " + currentUser.getDisplayName());

            LOGGER.info("First playlist name = " + spotifyApiWithAccessToken.getListOfCurrentUsersPlaylists().build().execute().getItems()[0].getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            LOGGER.error("Error: {}", e.getMessage());
        }

        return ResponseEntity.ok().body("ok");
    }

    public RedirectView redirectToHomePage(ResponseEntity responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return new RedirectView("http://localhost:8080");
        } else {
            return new RedirectView("http://localhost:8080/error");
        }
    }
}
