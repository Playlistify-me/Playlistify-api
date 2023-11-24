package io.playlistify.api.factories;

import io.playlistify.api.utils.environment.EnvType;
import io.playlistify.api.utils.environment.EnvVariable;

public class EnvVariableFactory {
    private EnvVariableFactory() {
    }

    public static EnvVariable getSystemEnvFromName(String envName) {
        return new EnvVariable(envName, EnvType.SYSTEM);
    }

    public static EnvVariable getLocalEnvFromName(String envName) {
        return new EnvVariable(envName, EnvType.LOCAL);
    }
}
