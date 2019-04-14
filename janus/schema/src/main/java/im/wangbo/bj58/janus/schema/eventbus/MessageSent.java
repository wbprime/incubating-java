package im.wangbo.bj58.janus.schema.eventbus;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class MessageSent {
    public abstract String httpMethod();

    public abstract String fullUri();

    public abstract JsonObject message();

    public static Builder builder() {
        return new AutoValue_MessageSent.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder httpMethod(String httpMethod);

        public abstract Builder fullUri(String fullUri);

        public abstract Builder message(JsonObject message);

        public abstract MessageSent build();
    }
}
