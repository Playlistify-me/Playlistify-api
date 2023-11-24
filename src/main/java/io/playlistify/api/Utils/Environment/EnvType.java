package io.playlistify.api.Utils.Environment;

public enum EnvType {
    SYSTEM("System"),
    LOCAL("Local");

    private final String text;

    EnvType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}