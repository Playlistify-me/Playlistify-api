package io.playlistify.api.models;



public enum ModelType {
    TRACK("track:"),
    PLAYLIST("playlist:");

    private final String modelUriPrefix;

    ModelType(String text) {
        final String spotifyUriStart = "spotify:";
        this.modelUriPrefix = spotifyUriStart + text;
    }

    public String getUriPrefix() {
        return this.modelUriPrefix;
    }

    public int getUriPrefixLength() {
        return this.modelUriPrefix.length();
    }

    public static ModelType[] getModelTypes() {
        return ModelType.values();
    }
}
