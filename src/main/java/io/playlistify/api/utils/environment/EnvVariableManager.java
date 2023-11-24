package io.playlistify.api.utils.environment;

import io.playlistify.api.factories.EnvVariableFactory;

import java.util.List;

public final class EnvVariableManager {
    private static final String SPOTIFY_CLIENT_ID = "SPOTIFY_CLIENT_ID";
    private static final String SPOTIFY_CLIENT_SECRET = "SPOTIFY_CLIENT_SECRET";
    private static final String SPOTIFY_REDIRECT_URL = "SPOTIFY_REDIRECT_URL";

    private static final String CLIENT_ID_VALUE = getEnvValue(SPOTIFY_CLIENT_ID);
    private static final String CLIENT_SECRET_VALUE = getEnvValue(SPOTIFY_CLIENT_SECRET);
    private static final String REDIRECT_URL_VALUE = getEnvValue(SPOTIFY_REDIRECT_URL);

    private EnvVariableManager() {
    }

    private static String getEnvValue(String envName) {
        final EnvVariable systemEnv = EnvVariableFactory.getSystemEnvFromName(envName);
        final EnvVariable localEnv = EnvVariableFactory.getLocalEnvFromName(envName);

        final List<EnvVariable> envVariableList = List.of(systemEnv, localEnv);

        boolean systemEnvNotNull = systemEnv.getNotNull();
        boolean localEnvNotNull = localEnv.getNotNull();

        if (systemEnvNotNull && !localEnvNotNull) {
            EnvVariableLogger.logVarsAndUsing(envVariableList, systemEnv);

            return systemEnv.getValue();
        } else if (!systemEnvNotNull && localEnvNotNull) {
            EnvVariableLogger.logVarsAndUsing(envVariableList, localEnv);

            return localEnv.getValue();
        } else if (systemEnvNotNull) {
            EnvVariableLogger.logVarsAndUsing(envVariableList, systemEnv);

            return systemEnv.getValue();
        } else {
            throw new IllegalArgumentException("No system or local environment variable found for " + envName);
        }
    }

    public static String getSpotifyClientIdValue() {
        return CLIENT_ID_VALUE;
    }

    public static String getSpotifyClientSecretValue() {
        return CLIENT_SECRET_VALUE;
    }

    public static String getSpotifyRedirectUrlValue() {
        return REDIRECT_URL_VALUE;
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