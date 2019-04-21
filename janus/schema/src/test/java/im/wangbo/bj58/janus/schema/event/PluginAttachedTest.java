package im.wangbo.bj58.janus.schema.event;

import org.junit.Ignore;
import org.junit.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Ignore
public class PluginAttachedTest {
    @Test
    public void testToJson() throws Exception {
        final PluginAttached pluginAttached = PluginAttached.create(100L, 1000L);

        final Jsonb jsonb = JsonbBuilder.create();

//        final String eventBody = jsonb.toJson(pluginAttached);
        final String json = jsonb.toJson(pluginAttached, PluginAttached.class);

        System.out.println(json);
    }

    @Test
    public void testFromJson() throws Exception {
        final String json = "{\"pluginHandleId\":1000,\"sessionId\":100}";

        final Jsonb jsonb = JsonbBuilder.create();

        final PluginAttached pluginAttached = jsonb.fromJson(json, PluginAttached.class);

        System.out.println(pluginAttached);
    }
}