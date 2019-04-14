package im.wangbo.bj58.janus.schema.transport;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

    @Before
    public void setUp() throws Exception {
        transport = HttpTransport.create();

//        transport.connect(URI.create("http://10.9.192.162:8888")).getRequest(1, TimeUnit.MINUTES);
        transport.connect(URI.create("https://janus.conf.meetecho.com/janus")).get(1, TimeUnit.MINUTES);
    }

    @Test
    public void test() throws Exception {
        final CompletableFuture<JsonObject> finished = new CompletableFuture<>();

        final CompletableFuture<Void> request = transport.handler(finished::complete)
                .send(Transport.RequestMessage.builder()
                        .request(RequestMethod.SERVER_INFO)
                        .transaction(TransactionId.of("wbprime" + System.currentTimeMillis()))
                        .build()
                );

        request.thenCompose(ignored -> finished)
                .whenComplete((re, ex) -> {
                    if (null == ex) System.out.println(re.toString());
                    else ex.printStackTrace();
                })
                .get(1L, TimeUnit.MINUTES);
    }
}