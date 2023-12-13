package io.playlistify.api.models;

public class TrackIdUriModel extends AbstractIdUriModel {
    public TrackIdUriModel(String id, String uri) {
        super(id, uri);
    }

    public TrackIdUriModel(String idOrUri) {
        super(idOrUri);
    }

    @Override
    protected ModelType getModelType() {
        return ModelType.TRACK;
    }
}
