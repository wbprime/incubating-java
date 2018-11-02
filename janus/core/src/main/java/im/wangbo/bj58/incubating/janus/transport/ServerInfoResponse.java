package im.wangbo.bj58.incubating.janus.transport;

import com.google.auto.value.AutoValue;

import java.util.Map;

import im.wangbo.bj58.incubating.janus.ServerInfo;
import im.wangbo.bj58.incubating.janus.Transaction;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class ServerInfoResponse {
    public abstract Transaction transaction();

    public abstract ServerInfo serverInfo();

    public static Builder builder() {
        return new AutoValue_ServerInfoResponse.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder transaction(Transaction transaction);

        abstract ServerInfo.Builder serverInfoBuilder();

        public final Builder server(final ServerInfo.ServerDesc server) {
            serverInfoBuilder().server(server);
            return this;
        }

        public final Builder plugins(final Map<String, ServerInfo.PluginDesc> plugins) {
            serverInfoBuilder().plugins(plugins);
            return this;
        }

        public final Builder eventHandlers(final Map<String, ServerInfo.EvHandlerDesc> eventHandlers) {
            serverInfoBuilder().eventHandlers(eventHandlers);
            return this;

        }

        public final Builder transports(final Map<String, ServerInfo.TransportDesc> transports) {
            serverInfoBuilder().transports(transports);
            return this;
        }

        public abstract ServerInfoResponse build();
    }
}
