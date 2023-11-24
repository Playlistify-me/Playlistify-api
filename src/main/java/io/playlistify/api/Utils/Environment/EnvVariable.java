package io.playlistify.api.Utils.Environment;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvVariable {
    private static final String ENV_FILE_NAME = ".env";
    private static final Dotenv dotenv = Dotenv.configure().filename(ENV_FILE_NAME).ignoreIfMissing().load();

    private final String name;
    private final EnvType type;
    private final String value;
    private final boolean notNull;
    
    public EnvVariable(String name, EnvType type) {
        this.name = setName(name);
        this.type = setType(type);
        this.value = setValue();
        this.notNull = setNotNull();
    }

    private String setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (name.equals("")) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        return name;
    }

    private EnvType setType(EnvType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        return type;
    }

    private String setValue() {
        if (this.type == EnvType.SYSTEM) {
            return System.getenv(this.name);
        } else if (this.type == EnvType.LOCAL) {
            return dotenv.get(this.name);
        }

        return null;
    }

    private boolean setNotNull() {
        return this.value != null;
    }

    public boolean getNotNull() {
        return this.notNull;
    }

    public String getTypeToString() {
        return this.type.toString();
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
