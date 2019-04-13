package im.wangbo.bj58.janus.schema.transport;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import im.wangbo.bj58.janus.schema.RequestMethod;

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
        final CompletableFuture<Void> request = transport.send(
                Transport.Request.builder()
                        .request(RequestMethod.SERVER_INFO)
                        .transaction("wbprime" + System.currentTimeMillis())
                        .build()
        );

        request.get(1, TimeUnit.MINUTES);

        Thread.sleep(10000L);
    }
}