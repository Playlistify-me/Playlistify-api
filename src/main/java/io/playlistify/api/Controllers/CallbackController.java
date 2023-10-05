package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Factories.SpotifyApiFactory;
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
    Logger logger = LoggerFactory.getLogger(CallbackController.class);
    private SpotifyApi spotifyApi = SpotifyApiFactory.getBasicSpotifyApi();

    @GetMapping("")
    public ResponseEntity<String> callback(@RequestParam String code) {
        logger.info("code = " + code);
        SpotifyApiAuthenticator spotifyApiAuthenticator = new SpotifyApiAuthenticator(code);

        SpotifyApi authenticatedSpotifyApi = spotifyApiAuthenticator.getSpotifyApi();

        logger.info("access code = " + authenticatedSpotifyApi.getAccessToken());

        logger.info("refresh token = " + authenticatedSpotifyApi.getRefreshToken());

        try {
            User currentUser = authenticatedSpotifyApi.getCurrentUsersProfile().build().execute();
            logger.info("user name = " + currentUser.getDisplayName());

            logger.info("First playlist name = " + authenticatedSpotifyApi.getListOfCurrentUsersPlaylists().build().execute().getItems()[0].getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return ResponseEntity.ok("callback");

        // redirect to home page
    }

    public RedirectView redirectToHomePage(ResponseEntity responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return new RedirectView("http://localhost:8080");
        } else {
            return new RedirectView("http://localhost:8080/error");
        }
    }
}
