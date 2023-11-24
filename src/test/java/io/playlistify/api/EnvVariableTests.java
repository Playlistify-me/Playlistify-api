package io.playlistify.api;

import io.playlistify.api.factories.EnvVariableFactory;
import io.playlistify.api.utils.environment.EnvType;
import io.playlistify.api.utils.environment.EnvVariable;
import io.playlistify.api.utils.environment.EnvVariableLogger;
import io.playlistify.api.utils.environment.EnvVariableManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnvVariableTests {

    @Test
    void envVariableCorrectNameTest() {
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
    void envVariableSetAsSystemAndLocalTextTest() {
        final String expectedSystemText = "System";
        final String expectedLocalText = "Local";

        final String actualEnvVariableId = EnvVariableManager.getConstSpotifyClientId();
        final String actualSystemText = EnvVariableFactory.getSystemEnvFromName(actualEnvVariableId).getTypeToString();
        final String actualLocalText = EnvVariableFactory.getLocalEnvFromName(actualEnvVariableId).getTypeToString();

        Assertions.assertEquals(expectedLocalText, actualLocalText);
        Assertions.assertEquals(expectedSystemText, actualSystemText);
    }

    @Test
    void envVariableFactoryNullNameExceptionTest() {
        final String expectedExceptionMessage = "Name cannot be null";

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            EnvVariableFactory.getSystemEnvFromName(null);
        });

        final String actualExceptionMessage = exception.getMessage();

        Assertions.assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    void envVariableFactoryEmptyNameExceptionTest() {
        final String expectedExceptionMessage = "Name cannot be empty";
        final String emptyString = "";

        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            EnvVariableFactory.getSystemEnvFromName(emptyString);
        });

        final String actualExceptionMessage = exception.getMessage();

        Assertions.assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    void envVariableNotNullTest() {
        final String envVariableId = EnvVariableManager.getConstSpotifyClientId();
        final EnvType envType = EnvType.LOCAL;

        final EnvVariable localEnvVariableId = new EnvVariable(envVariableId, envType);

        Assertions.assertTrue(localEnvVariableId.getNotNull());
    }

    @Test
    public void privateConstructorTest() throws Exception {
        TestUtils.assertPrivateConstructor(EnvVariableManager.class);
        TestUtils.assertPrivateConstructor(EnvVariableFactory.class);
        TestUtils.assertPrivateConstructor(EnvVariableLogger.class);
    }
}
