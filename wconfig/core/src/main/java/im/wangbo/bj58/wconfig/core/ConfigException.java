package im.wangbo.bj58.wconfig.core;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class ConfigException extends Exception {
    public ConfigException() {
        super();
    }

    public ConfigException(final String message) {
        super(message);
    }

    public ConfigException(final Throwable cause) {
        super(cause);
    }

    public ConfigException(final String message, final  Throwable cause) {
        super(message, cause);
    }
}
