package im.wangbo.bj58.janus;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

import java.time.Duration;
import java.util.Map;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class ServerInfo {
    public abstract ServerDesc server();

    public abstract ImmutableMap<String, PluginDesc> plugins();
    public abstract ImmutableMap<String, EvHandlerDesc> eventHandlers();
    public abstract ImmutableMap<String, TransportDesc> transports();

    public static Builder builder() {
        return new AutoValue_ServerInfo.Builder();
    }

    @AutoValue
    public abstract static class ServerDesc {
        public abstract String name();
        public abstract String author();
        public abstract Integer versionNumber();
        public abstract String versionString();
        public abstract Duration sessionTimeout();

        public static Builder builder() {
            return new AutoValue_ServerInfo_ServerDesc.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder name(String name);

            public abstract Builder author(String author);

            public abstract Builder versionNumber(Integer versionNumber);

            public abstract Builder versionString(String versionString);

            public abstract Builder sessionTimeout(Duration sessionTimeout);

            public abstract ServerDesc build();
        }
    }

    @AutoValue
    public abstract static class PluginDesc {
        public abstract String name();
        public abstract String author();
        public abstract Integer versionNumber();
        public abstract String versionString();
        public abstract String description();

        public static Builder builder() {
            return new AutoValue_ServerInfo_PluginDesc.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder name(String name);

            public abstract Builder author(String author);

            public abstract Builder versionNumber(Integer versionNumber);

            public abstract Builder versionString(String versionString);

            public abstract Builder description(String description);

            public abstract PluginDesc build();
        }
    }

    @AutoValue
    public abstract static class TransportDesc {
        public abstract String name();
        public abstract String author();
        public abstract Integer versionNumber();
        public abstract String versionString();
        public abstract String description();

        public static Builder builder() {
            return new AutoValue_ServerInfo_TransportDesc.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder name(String name);

            public abstract Builder author(String author);

            public abstract Builder versionNumber(Integer versionNumber);

            public abstract Builder versionString(String versionString);

            public abstract Builder description(String description);

            public abstract TransportDesc build();
        }
    }

    @AutoValue
    public abstract static class EvHandlerDesc {
        public abstract String name();
        public abstract String author();
        public abstract Integer versionNumber();
        public abstract String versionString();
        public abstract String description();

        public static Builder builder() {
            return new AutoValue_ServerInfo_EvHandlerDesc.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder name(String name);

            public abstract Builder author(String author);

            public abstract Builder versionNumber(Integer versionNumber);

            public abstract Builder versionString(String versionString);

            public abstract Builder description(String description);

            public abstract EvHandlerDesc build();
        }
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder server(final ServerDesc server);

        public abstract Builder plugins(final Map<String, PluginDesc> plugins);

        public abstract Builder eventHandlers(final Map<String, EvHandlerDesc> eventHandlers);

        public abstract Builder transports(final Map<String, TransportDesc> transports);

        public abstract ServerInfo build();
    }
}
