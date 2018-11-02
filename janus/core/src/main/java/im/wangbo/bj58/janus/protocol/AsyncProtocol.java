package im.wangbo.bj58.janus.protocol;

import com.google.common.util.concurrent.ListenableFuture;

import java.net.URI;
import java.util.List;

import javax.json.JsonObject;

import im.wangbo.bj58.janus.Candidate;
import im.wangbo.bj58.janus.Jsep;
import im.wangbo.bj58.janus.PluginHandle;
import im.wangbo.bj58.janus.ServerInfo;
import im.wangbo.bj58.janus.Session;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface AsyncProtocol {
    ListenableFuture<Void> connect(final URI uri);
    ListenableFuture<Void> close();

    ListenableFuture<ServerInfo> info();

    ListenableFuture<Session> create() throws InterruptedException;
    ListenableFuture<Void> destroy(final Session session) throws InterruptedException;

    ListenableFuture<Void> keepAlive(final Session session) throws InterruptedException;
    ListenableFuture<PluginHandle> attach(final Session session, final String plugin) throws InterruptedException;
    ListenableFuture<Void> detach(final PluginHandle handle) throws InterruptedException;

    /**
     * Send "trickle" message with given candidates.
     *
     * If {@code candidates} is empty, send "trickle" with candidates sending completed;
     * otherwise send all candidates via "trickle" message.
     *
     * @param handle plugin handle
     * @param candidates candidates
     * @return future
     */
    ListenableFuture<Void> trickle(
            final PluginHandle handle, final List<Candidate> candidates
    );

    ListenableFuture<Void> request(
            final PluginHandle handle, final JsonObject body
    );
    ListenableFuture<Void> request(
            final PluginHandle handle, final JsonObject body, final Jsep jsep
    );
}
