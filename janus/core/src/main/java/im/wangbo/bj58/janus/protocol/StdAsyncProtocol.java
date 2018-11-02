package im.wangbo.bj58.janus.protocol;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonObject;

import im.wangbo.bj58.janus.Candidate;
import im.wangbo.bj58.janus.Jsep;
import im.wangbo.bj58.janus.PluginHandle;
import im.wangbo.bj58.janus.ServerInfo;
import im.wangbo.bj58.janus.Session;
import im.wangbo.bj58.janus.Transaction;
import im.wangbo.bj58.janus.transport.GlobalRequest;
import im.wangbo.bj58.janus.transport.PluginHandleRequest;
import im.wangbo.bj58.janus.transport.SessionRequest;
import im.wangbo.bj58.janus.transport.Transport;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class StdAsyncProtocol implements AsyncProtocol {
    private final Transport transport;

    private final Map<Transaction, FutureCallback<Object>> responseCallbacks = Maps.newConcurrentMap();

    private StdAsyncProtocol(final Transport transport) {
        this.transport = transport;
    }

    public static StdAsyncProtocol create(final Transport transport) {
        return new StdAsyncProtocol(transport);
    }

    private Transaction newTransaction() {
        while (true) {
            final Transaction t = Transaction.of();
            if (! responseCallbacks.containsKey(t)) {
                return t;
            }
        }
    }

    @Override
    public ListenableFuture<Void> connect(final URI uri) {
        final SettableFuture<Void> promise = SettableFuture.create();
        transport.connect(uri,
                StdResponseHandler.builder()
                        .serverInfoHandler(
                                e -> {
                                    final FutureCallback<Object> callback = responseCallbacks.remove(e.transaction());
                                    if (null != callback) {
                                        callback.onSuccess(e.serverInfo());
                                    }

                                    // TODO handle callback not found
                                }
                        )
                        .janusErrorHandler(
                                e -> {
                                    final FutureCallback<Object> callback = responseCallbacks.remove(e.transaction());
                                    if (null != callback) {
                                        callback.onFailure(
                                                new Exception(e.error().toString())
                                        );
                                    }

                                    // TODO handle callback not found
                                }
                        )
                        .janusAckHandler(
                                e -> {
                                    final FutureCallback<Object> callback = responseCallbacks.remove(e.transaction());
                                    if (null != callback) {
                                        callback.onSuccess(e);
                                    }

                                    // TODO handle callback not found
                                }
                        )
                        .janusSuccessHandler(
                                e -> {
                                    final FutureCallback<Object> callback = responseCallbacks.remove(e.transaction());
                                    if (null != callback) {
                                        callback.onSuccess(e.data());
                                    }

                                    // TODO handle callback not found
                                }
                        )
                        .build()
        ).handle((v, ex) -> {
            if (null != ex) promise.setException(ex);
            else promise.set(v);
            return promise;
        });

        return promise;
    }

    @Override
    public ListenableFuture<Void> close() {
        final SettableFuture<Void> promise = SettableFuture.create();
        transport.close()
        .handle((v, ex) -> {
            if (null != ex) promise.setException(ex);
            else promise.set(v);
            return promise;
        });
        return promise;
    }

    @Override
    public ListenableFuture<ServerInfo> info() {
        final Transaction t = newTransaction();

        final SettableFuture<ServerInfo> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull final Object obj) {
                        result.set((ServerInfo) obj);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        transport.send(
                GlobalRequest.builder()
                        .request("info")
                        .transaction(t)
                        .build()
                );

        return result;
    }

    @Override
    public ListenableFuture<Session> create() throws InterruptedException {
        final Transaction t = newTransaction();

        final SettableFuture<Session> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        final JsonObject json = (JsonObject) obj;
                        result.set(Session.of(json.getJsonNumber("id").longValue()));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        transport.send(
                GlobalRequest.builder()
                        .request("create")
                        .transaction(t)
                        .build()
        );

        return result;
    }

    @Override
    public ListenableFuture<Void> destroy(final Session session) throws InterruptedException {
        final Transaction t = newTransaction();

        final SettableFuture<Void> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        result.set(null);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        transport.send(
                SessionRequest.builder()
                        .request("destroy")
                        .transaction(t)
                        .session(session)
                        .build()
        );

        return result;
    }

    @Override
    public ListenableFuture<Void> keepAlive(final Session session) throws InterruptedException {
        final Transaction t = newTransaction();

        final SettableFuture<Void> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        result.set(null);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        transport.send(
                SessionRequest.builder()
                        .request("keepalive")
                        .transaction(t)
                        .session(session)
                        .build()
        );

        return result;
    }

    @Override
    public ListenableFuture<PluginHandle> attach(final Session session, final String plugin) {
        final Transaction t = newTransaction();

        final SettableFuture<PluginHandle> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        final JsonObject json = (JsonObject) obj;
                        result.set(PluginHandle.of(session, json.getJsonNumber("id").longValue()));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        transport.send(
                SessionRequest.builder()
                        .request("attach")
                        .transaction(t)
                        .session(session)
                        .message(Json.createObjectBuilder().add("plugin", plugin).build())
                        .build()
        );

        return result;
    }

    @Override
    public ListenableFuture<Void> detach(final PluginHandle handle) throws InterruptedException {
        final Transaction t = newTransaction();

        final SettableFuture<Void> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        result.set(null);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        transport.send(
                PluginHandleRequest.builder()
                        .request("detach")
                        .transaction(t)
                        .pluginHandle(handle)
                        .build()
        );

        return result;
    }

    @Override
    public ListenableFuture<Void> trickle(
            final PluginHandle handle, final List<Candidate> candidates
    ) {
        final Transaction t = newTransaction();

        final SettableFuture<String> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        result.set(null);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        final SettableFuture<Void> promise = SettableFuture.create();
        transport.send(
                PluginHandleRequest.builder()
                        .request("trickle")
                        .transaction(t)
                        .pluginHandle(handle)
                        .candidates(candidates)
                        .build()
        ).handle((v, ex) -> {
            if (null != ex) promise.setException(ex);
            else promise.set(v);
            return promise;
        });
        return promise;
    }

    @Override
    public ListenableFuture<Void> request(
            final PluginHandle handle, final JsonObject body
    ) {
        return request_private(handle, body, null);
    }

    @Override
    public ListenableFuture<Void> request(
            final PluginHandle handle, final JsonObject body, final Jsep jsep
    ) {
        return request_private(handle, body, jsep);
    }

    private ListenableFuture<Void> request_private(
            final PluginHandle handle, final JsonObject body, @Nullable final Jsep jsep
    ) {
        final Transaction t = newTransaction();

        final SettableFuture<Void> result = SettableFuture.create();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        result.set(null);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        result.setException(t);
                    }
                }
        );

        transport.send(
                PluginHandleRequest.builder()
                        .request("message")
                        .transaction(t)
                        .pluginHandle(handle)
                        .data(body)
                        .jsep(jsep)
                        .build()
        );

        return result;
    }
}
