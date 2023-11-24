package io.playlistify.api;

import io.playlistify.api.Factories.EnvVariableFactory;
import io.playlistify.api.Utils.Environment.EnvType;
import io.playlistify.api.Utils.Environment.EnvVariable;
import io.playlistify.api.Utils.Environment.EnvVariableManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnvVariableTests {

    @Test
    void EnvVariableCorrectNameTest( ) {
        final String expectedEnvVariableId = "SPOTIFY_CLIENT_ID";
        final String actualEnvVariableId = EnvVariableManager.getConstSpotifyClientId();

        final String expectedEnvVariableSecret = "SPOTIFY_CLIENT_SECRET";
        final String actualEnvVariableSecret = EnvVariableManager.getConstSpotifyClientSecret();

        final String expectedEnvVariableRedirectUrl = "SPOTIFY_REDIRECT_URL";
        final String actualEnvVariableRedirectUrl = EnvVariableManager.getConstSpotifyRedirectUrl();


        Assertions.assertEquals(expectedEnvVariableId, actualEnvVariableId);
        Assertions.assertEquals(expectedEnvVariableSecret, actualEnvVariableSecret);
        Assertions.assertEquals(expectedEnvVariableRedirectUrl, actualEnvVariableRedirectUrl);
    }

    @Test
    void EnvVariableSetAsSystemAndLocalTextTest() {
        final String expectedSystemText = "System";
        final String expectedLocalText = "Local";

        final String actualEnvVariableId = EnvVariableManager.getConstSpotifyClientId();
        final String actualSystemText = EnvVariableFactory.getSystemEnvFromName(actualEnvVariableId).getTypeToString();
        final String actualLocalText = EnvVariableFactory.getLocalEnvFromName(actualEnvVariableId).getTypeToString();

        Assertions.assertEquals(expectedLocalText, actualLocalText);
        Assertions.assertEquals(expectedSystemText, actualSystemText);
    }

    @Test
    void EnvVariableFactoryNullNameExceptionTest() {
        final String expectedExceptionMessage = "Name cannot be null";

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            EnvVariableFactory.getSystemEnvFromName(null);
        });

        final String actualExceptionMessage = exception.getMessage();

        Assertions.assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    void EnvVariableFactoryEmptyNameExceptionTest() {
        final String expectedExceptionMessage = "Name cannot be empty";
        final String emptyString = "";

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            EnvVariableFactory.getSystemEnvFromName(emptyString);
        });

        final String actualExceptionMessage = exception.getMessage();

        Assertions.assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    void EnvVariableNotNullTest() {
        final String envVariableId = EnvVariableManager.getConstSpotifyClientId();
        final EnvType envType = EnvType.LOCAL;

        final EnvVariable localEnvVariableId = new EnvVariable(envVariableId, envType);

        Assertions.assertTrue(localEnvVariableId.getNotNull());
    }
}
