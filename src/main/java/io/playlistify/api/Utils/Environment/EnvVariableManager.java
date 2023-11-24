package io.playlistify.api.Utils.Environment;

import io.playlistify.api.Factories.EnvVariableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class EnvVariableManager {
    private static final Logger logger = LoggerFactory.getLogger(EnvVariableManager.class);

    private static final String SPOTIFY_CLIENT_ID = "SPOTIFY_CLIENT_ID";
    private static final String SPOTIFY_CLIENT_SECRET = "SPOTIFY_CLIENT_SECRET";
    private static final String SPOTIFY_REDIRECT_URL = "SPOTIFY_REDIRECT_URL";

    private static final String spotifyClientId = getEnvValue(SPOTIFY_CLIENT_ID);
    private static final String spotifyClientSecret = getEnvValue(SPOTIFY_CLIENT_SECRET);
    private static final String spotifyRedirectUrl = getEnvValue(SPOTIFY_REDIRECT_URL);

    private EnvVariableManager() {}

    private static String getEnvValue(String envName) {
        final EnvVariable systemEnv = EnvVariableFactory.getSystemEnvFromName(envName);
        final EnvVariable localEnv = EnvVariableFactory.getLocalEnvFromName(envName);

        final List<EnvVariable> envVariableList = List.of(systemEnv, localEnv);

        boolean systemEnvNotNull = systemEnv.getNotNull();
        boolean localEnvNotNull = localEnv.getNotNull();

        if (systemEnvNotNull & !localEnvNotNull) {
            EnvVariableLogger.logVarsAndUsing(envVariableList, systemEnv);

            return systemEnv.getValue();
        } else if (!systemEnvNotNull & localEnvNotNull) {
            EnvVariableLogger.logVarsAndUsing(envVariableList, localEnv);

            return localEnv.getValue();
        } else if (systemEnvNotNull & localEnvNotNull) {
            EnvVariableLogger.logVarsAndUsing(envVariableList, systemEnv);

            return systemEnv.getValue();
        } else {
            throw new IllegalArgumentException("No system or local environment variable found for " + envName);
        }
    }

    public static String getSpotifyClientId() {
        return spotifyClientId;
    }

    public static String getSpotifyClientSecret() {
        return spotifyClientSecret;
    }

    public static String getSpotifyRedirectUrl() {
        return spotifyRedirectUrl;
    }

    public static String getConstSpotifyClientId() {
        return SPOTIFY_CLIENT_ID;
    }

    public static String getConstSpotifyClientSecret() {
        return SPOTIFY_CLIENT_SECRET;
    }

    public static String getConstSpotifyRedirectUrl() {
        return SPOTIFY_REDIRECT_URL;
    }
}