package im.wangbo.bj58.janus.schema.transport;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.json.JsonObject;

import im.wangbo.bj58.janus.schema.RequestMethod;
import im.wangbo.bj58.janus.schema.TransactionId;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Ignore
public class HttpTransportTest {
    private HttpTransport transport;

    private final Consumer<JsonObject> handler = re -> log(re, null);
    private final Consumer<Throwable> exHandler = ex -> log(null, ex);

    @Before
    public void setUp() throws Exception {
        transport = HttpTransport.create();

//        transport.connect(URI.create("http://10.9.192.162:8888")).getRequest(1, TimeUnit.MINUTES);
        transport.connect(URI.create("https://janus.conf.meetecho.com/janus")).get(1, TimeUnit.MINUTES);
    }

    private void log(final JsonObject json, final Throwable ex) {
        if (null != ex) {
            System.out.println("Log exception: " + ex.getMessage());
            ex.printStackTrace();
        } else {
            System.out.println("Log JSON result");
            json.forEach((k, v) -> System.out.println(k + " => " + v));
        }
    }

    @Test
    public void test_unsupportedMethod() throws Exception {
        final CompletableFuture<JsonObject> finished = new CompletableFuture<>();

        final CompletableFuture<Void> request = transport.handler(finished::complete)
                .send(Transport.RequestMessage.builder()
                        .request(RequestMethod.of("random" + System.currentTimeMillis()))
                        .transaction(TransactionId.of("wbprime" + System.currentTimeMillis()))
                        .build()
                );

        request.thenCompose(ignored -> finished)
                .whenComplete(this::log)
                .get(1L, TimeUnit.MINUTES);
    }

    @Test
    public void test_serverInfo() throws Exception {
        final CompletableFuture<JsonObject> finished = new CompletableFuture<>();

        final CompletableFuture<Void> request = transport.handler(finished::complete)
                .send(Transport.RequestMessage.serverInfoMessageBuilder()
                        .transaction(TransactionId.of("wbprime" + System.currentTimeMillis()))
                        .build()
                );

        request.thenCompose(ignored -> finished)
                .whenComplete(this::log)
                .get(1L, TimeUnit.MINUTES);
    }

    @Test
    public void test_createSession() throws Exception {
        {
             transport.handler(handler)
                    .exceptionHandler(exHandler)
                    .send(Transport.RequestMessage.createSessionMessageBuilder()
                            .transaction(TransactionId.of("wbprime" + System.currentTimeMillis()))
                            .build()
                    ).get(1L, TimeUnit.MINUTES);
        }

        Thread.sleep(TimeUnit.MINUTES.toMillis(1L));
//        Thread.sleep(TimeUnit.SECONDS.toMillis(2L));
    }
}