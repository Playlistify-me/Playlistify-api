package io.playlistify.api.utils.model;

import io.playlistify.api.models.ModelType;

public class UriChecker {
    private UriChecker() {
    }

    public static boolean IsUri(String IdOrUri, ModelType modelType) {
        return IdOrUri.startsWith(modelType.getUriPrefix());
    }
}
