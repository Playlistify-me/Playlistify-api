package io.playlistify.api.models;

import io.playlistify.api.utils.model.Converter;
import io.playlistify.api.utils.model.UriChecker;

public abstract class AbstractIdUriModel {
    private final String id;
    private final String uri;

    protected AbstractIdUriModel(String id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    protected AbstractIdUriModel(String idOrUri) {
        ModelType modelType = getModelType();

        // maybe move uri checker into this class instead
        if (UriChecker.IsUri(idOrUri, modelType)) {
            this.id = Converter.ConvertToId(idOrUri, modelType);
            this.uri = idOrUri;
        } else {
            this.id = idOrUri;
            this.uri = Converter.ConvertToUri(idOrUri, modelType);
        }
    }

    public final String getId() {
        return id;
    }

    public final String getUri() {
        return uri;
    }

    /**
     * @implSpec The {@link ModelType} of the subclass.
     */
    protected abstract ModelType getModelType();
}
