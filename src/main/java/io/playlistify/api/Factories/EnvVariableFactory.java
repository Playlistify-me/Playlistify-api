package io.playlistify.api.Factories;

import io.playlistify.api.Utils.Environment.EnvType;
import io.playlistify.api.Utils.Environment.EnvVariable;

public class EnvVariableFactory {
    public static EnvVariable getSystemEnvFromName(String envName) {
        return new EnvVariable(envName, EnvType.SYSTEM);
    }

    public static EnvVariable getLocalEnvFromName(String envName) {
        return new EnvVariable(envName, EnvType.LOCAL);
    }
}
