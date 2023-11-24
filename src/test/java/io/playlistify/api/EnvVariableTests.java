package io.playlistify.api;

import io.playlistify.api.Factories.EnvVariableFactory;
import io.playlistify.api.Utils.Environment.EnvVariableManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnvVariableTests {

    @Test
    void EnvVariableNameTest( ) {
        final String expectedEnvVariableName = "SPOTIFY_CLIENT_ID";
        final String actualEnvVariableName = EnvVariableManager.getConstSpotifyClientId();

        Assertions.assertEquals(expectedEnvVariableName, actualEnvVariableName);
    }

    @Test
    void EnvVariableSetAsSystemTextTest() {
        final String expectedText = "System";

        final String actualEnvVariableName = EnvVariableManager.getConstSpotifyClientId();
        final String actualText = EnvVariableFactory.getSystemEnvFromName(actualEnvVariableName).getTypeToString();

        Assertions.assertEquals(expectedText, actualText);
    }
}
