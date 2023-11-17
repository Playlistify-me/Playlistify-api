package io.playlistify.api.Utils;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvVariableManager {
    private static final String SPOTIFY_CLIENT_ID = "SPOTIFY_CLIENT_ID";
    private static final String SPOTIFY_CLIENT_SECRET = "SPOTIFY_CLIENT_SECRET";
    private static final String SPOTIFY_REDIRECT_URL = "SPOTIFY_REDIRECT_URL";

    private static final String ENV_FILE_NAME = ".env";

    private static final Dotenv dotenv = Dotenv.configure().filename(ENV_FILE_NAME).ignoreIfMissing().load();

    private static String spotifyClientId = SetEnv(SPOTIFY_CLIENT_ID);
    private static String spotifyClientSecret = SetEnv(SPOTIFY_CLIENT_SECRET);
    private static String spotifyRedirectUrl = SetEnv(SPOTIFY_REDIRECT_URL);

    private static String SetEnv(String envName) {
        final String systemEnv = SetSystemEnv(envName);
        final String localEnv = SetLocalEnv(envName);

        final String lineBreak = "========================================";

        boolean systemEnvNotNull = StringNotNull(systemEnv);
        boolean localEnvNotNull = StringNotNull(localEnv);

        if (systemEnvNotNull & !localEnvNotNull) {
            System.out.println("System environment variable found for:" + envName);
            System.out.println("Local environment variable not found for: " + envName);
            System.out.println("Using system environment variable for: " + envName);
            System.out.println(lineBreak);

            return systemEnv;
        } else if (!systemEnvNotNull & localEnvNotNull) {
            System.out.println("System environment variable not found for: " + envName);
            System.out.println("Local environment variable found for: " + envName);
            System.out.println("Using system environment variable for: " + envName);
            System.out.println(lineBreak);

            return localEnv;
        } else if (systemEnvNotNull & localEnvNotNull) {
            System.out.println("System environment variable found for: " + envName);
            System.out.println("Local environment variable found for: " + envName);
            System.out.println("Discarding local environment variable. Using system environment variable for: " + envName);
            System.out.println(lineBreak);

            return systemEnv;
        } else {
            throw new IllegalArgumentException("No system or local environment variable found for " + envName);
        }
    }
    
    private static boolean StringNotNull(String value) {
        return value != null;
    }

    private static String SetSystemEnv(String envName) {
        final String envValue = System.getenv(envName);

        return envValue;
    }

    private static String SetLocalEnv(String envName) {
        final String envValue = dotenv.get(envName, null);

        return envValue;
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
