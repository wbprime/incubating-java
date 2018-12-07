package im.wangbo.bj58.wconfig.core;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@FunctionalInterface
public interface ConfigChangeHandler {
    void onChanged(final JsonObject oldJson, final JsonObject newJson);
}
