package io.playlistify.api.models;

public class PlaylistIdUriModel extends AbstractIdUriModel {
    public PlaylistIdUriModel(String id, String uri) {
        super(id, uri);
    }

    public PlaylistIdUriModel(String idOrUri) {
        super(idOrUri);
    }

    @Override
    public ModelType getModelType() {
        return ModelType.PLAYLIST;
    }
}
