package im.wangbo.bj58.janus.schema.transport;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import javax.json.JsonObject;
import java.util.Optional;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-10, by Elvis Wang
 */
@AutoValue
public abstract class Request {
    public abstract Type type();

    public abstract Optional<SessionId> sessionId();

    public abstract Optional<PluginId> pluginId();

    public abstract TransactionId transaction();

    public abstract JsonObject root();

    public static Builder builder() {
        return new AutoValue_Request.Builder().root(JsonObject.EMPTY_JSON_OBJECT);
    }

    public static Builder serverInfoMessageBuilder() {
        return builder().type(Type.SERVER_INFO);
    }

    public static Builder createSessionMessageBuilder() {
        return builder().type(Type.CREATE_SESSION);
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder type(final Type type);

        public abstract Builder transaction(final TransactionId transaction);

        abstract Builder sessionId(final Optional<SessionId> sessionId);

        public final Builder sessionId(@Nullable final SessionId sessionId) {
            return sessionId(Optional.ofNullable(sessionId));
        }

        abstract Builder pluginId(final Optional<PluginId> pluginId);

        public final Builder pluginId(@Nullable final PluginId v) {
            return pluginId(Optional.ofNullable(v));
        }

        public abstract Builder root(final JsonObject root);

        public abstract Request build();
    }

    public enum Type {
        CREATE_SESSION("create"),
        DESTROY_SESSION("destroy"),
        ATTACH_PLUGIN("attach"),
        DETACH_PLUGIN("detach"),
        HANGUP_PLUGIN("hangup"),
        SEND_MESSAGE("message"),
        TRICKLE("trickle"),
        SERVER_INFO("info");

        private final String m;

        Type(final String m) {
            this.m = m;
        }

        public String type() {
            return m;
        }

        public static Optional<Type> parse(final String str) {
            for (final Type value : Type.values()) {
                if (value.type().equalsIgnoreCase(str)) return Optional.of(value);
            }
            return Optional.empty();
        }
    }
}
