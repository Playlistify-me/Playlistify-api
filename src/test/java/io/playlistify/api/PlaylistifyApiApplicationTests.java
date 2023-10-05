package io.playlistify.api;

import io.playlistify.api.Authorization.SpotifyApiAuthenticator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

@SpringBootTest
class PlaylistifyApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void authUriTest() {
		URI expectedAuthUri = URI.create("https://accounts.spotify.com:443/authorize?client_id=ff5da2008124422385f9dc729dc7df9a&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fcallback");
		URI actualAuthUri = new SpotifyApiAuthenticator().getAuthCodeUri();

		Assertions.assertEquals(expectedAuthUri, actualAuthUri);
	}
}
