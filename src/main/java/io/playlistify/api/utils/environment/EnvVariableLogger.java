package io.playlistify.api.utils.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EnvVariableLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvVariableManager.class);

    private EnvVariableLogger() {
    }

    public static void logUsing(EnvVariable envVariable) {
        final String using = "Using ";
        final String forEnv = " environment variable for: ";
        final String lineBreak = "========================================";

        final String envName = envVariable.getName();
        final String envTypeString = envVariable.getTypeToString();

        LOGGER.info("{}{}{}{}", using, envTypeString, forEnv, envName);
        LOGGER.info(lineBreak);
    }

    public static void logVars(List<EnvVariable> envVariableList) {
        for (EnvVariable envVariable : envVariableList) {
            logVar(envVariable);
        }
    }

    public static void logVar(EnvVariable envVariable) {
        final String foundFor = " environment variable found for: ";
        final String notFoundFor = " environment variable NOT found for: ";

        final String envName = envVariable.getName();
        final String envTypeString = envVariable.getTypeToString();
        final boolean envVariableNotNull = envVariable.getNotNull();

        if (envVariableNotNull) {
            LOGGER.info("{}{}{}", envTypeString, foundFor, envName);
        } else {
            LOGGER.warn("{}{}{}", envTypeString, notFoundFor, envName);
        }
    }

    public static void logVarsAndUsing(List<EnvVariable> envVariableList, EnvVariable usingEnvVariable) {
        logVars(envVariableList);
        logUsing(usingEnvVariable);
    }
}
