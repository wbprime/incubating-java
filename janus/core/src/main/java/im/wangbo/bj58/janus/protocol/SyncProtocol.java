package im.wangbo.bj58.janus.protocol;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import im.wangbo.bj58.janus.Candidate;
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
public interface SyncProtocol {
//    void registerCallback(final JanusAckHandler handler);
//    void registerCallback(final JanusErrorHandler handler);
//    void registerCallback(final JanusEventHandler handler);
//    void registerCallback(final WebrtcHangupHandler handler);
//    void registerCallback(final WebrtcMediaHandler handler);
//    void registerCallback(final WebrtcSlowlinkHandler handler);
//    void registerCallback(final JanusSuccessHandler handler);
//    void registerCallback(final WebrtcUpHandler handler);

    ListenableFuture<Void> connect();
    ListenableFuture<Void> close();

    ListenableFuture<ServerInfo> info();

    Session create() throws InterruptedException;
    boolean destroy(final Session session) throws InterruptedException;

    boolean keepAlive(final Session session) throws InterruptedException;
    PluginHandle attach(final Session session, final String plugin) throws InterruptedException;
    boolean detach(final PluginHandle handle) throws InterruptedException;

    /**
     * Send "trickle" message with given candidates.
     *
     * If {@code candidates} is empty, send "trickle" with candidates sending completed;
     * otherwise send all candidates via "trickle" message.
     *
     * @param session session
     * @param handle plugin handle
     * @param candidates candidates
     * @return future
     */
    ListenableFuture<Void> trickle(
            final Session session, final PluginHandle handle, final List<Candidate> candidates
    );

    boolean request(final String req, final String jsep) throws InterruptedException;
}
