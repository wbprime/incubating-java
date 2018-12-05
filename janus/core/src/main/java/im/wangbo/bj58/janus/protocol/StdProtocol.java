package im.wangbo.bj58.janus.protocol;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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
import im.wangbo.bj58.janus.transport.TransportFactory;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class StdProtocol {
    private final Transport transport;
    private final RespHandler respHandler;

    private final Map<Transaction, FutureCallback<Object>> responseCallbacks = Maps.newConcurrentMap();

    private StdProtocol(final Transport transport, final RespHandler handler) {
        this.transport = transport;
        this.respHandler = handler;
    }

    public static Future<StdProtocol> connect(final TransportFactory transportFactory, final URI uri) {
        final SettableFuture<Void> promise = SettableFuture.create();

        final RespHandler respHandler = new RespHandler();
        return transportFactory.connect(uri, respHandler)
                .thenApply(transport -> new StdProtocol(transport, respHandler));
    }

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

    public CompletableFuture<ServerInfo> info() {
        final Transaction t = newTransaction();

        final CompletableFuture<ServerInfo> future = new CompletableFuture<>();
        transport.send(GlobalRequest.builder().request("info").transaction(t).build())
                .whenComplete((result, throwable) -> {
                    if (null != throwable) {
                        future.completeExceptionally(throwable);
                    } else {
                        respHandler.register(t, future);
                    }
                });
        return future;
    }

    public CompletableFuture<Session> createSession() throws InterruptedException {
        final Transaction t = newTransaction();

        final CompletableFuture<Session> result = new CompletableFuture<>();
        responseCallbacks.put(
                t,
                new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nonnull Object obj) {
                        final JsonObject json = (JsonObject) obj;
                        result.complete(Session.of(json.getJsonNumber("id").longValue()));
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

    public ListenableFuture<Void> request(
            final PluginHandle handle, final JsonObject body
    ) {
        return request_private(handle, body, null);
    }

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

    private Transaction newTransaction() {
        while (true) {
            final Transaction t = Transaction.of();
            if (!responseCallbacks.containsKey(t)) {
                return t;
            }
        }
    }
}
