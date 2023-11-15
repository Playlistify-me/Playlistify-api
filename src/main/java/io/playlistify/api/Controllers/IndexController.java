package io.playlistify.api.Controllers;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import io.playlistify.api.Factories.SpotifyApiFactory;
import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.net.URI;


@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        URI authCodeURI = SpotifyApiAuthenticator.generateAuthCodeUri();
        return "uri = " + authCodeURI.toString();
    }

    @GetMapping("/test")
    public String test() {
        SpotifyApi spotifyApiCC = SpotifyApiFactory.getSpotifyApiClientCredentials();

        String spotifyDevId = "31htnbwollsrbp7lmf3uvwq3h3pu";
        String testDevId = "";

        try {
            testDevId = spotifyApiCC.getUsersProfile(spotifyDevId).build().execute().getId();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return testDevId;
    }

    @GetMapping("/testUri")
    public RedirectView testUri() {
        URI authCodeURI = SpotifyApiAuthenticator.generateAuthCodeUri();
        return new RedirectView(authCodeURI.toString());
    }


}
