package io.playlistify.api.utils.model;

import io.playlistify.api.models.ModelType;

public class Converter {
    private Converter() {
    }

    public static String ConvertToUri(String id, ModelType modelType) {
        return switch (modelType) {
            case TRACK, PLAYLIST -> modelType.getUriPrefix();
        } + id;
    }

    public static String ConvertToId(String uri, ModelType modelType) {
        int beginIndex = switch (modelType) {
            case TRACK, PLAYLIST -> modelType.getUriPrefixLength();
        };

        return uri.substring(beginIndex);
    }
}