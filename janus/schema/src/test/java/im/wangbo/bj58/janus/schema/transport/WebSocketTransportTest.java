package im.wangbo.bj58.janus.schema.transport;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.json.Json;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class WebSocketTransportTest {
    WebSocketTransport transport;

    @Before
    public void setUp() throws Exception {
        transport = WebSocketTransport.create();

        transport.connect(URI.create("ws://10.9.192.162:8888")).get(1, TimeUnit.MINUTES);
    }

    @Test
    public void test() {
        transport.request(
                Json.createObjectBuilder()
                        .add("janus", "info")
                        .add("transaction", "server_info")
                        .build()
        );
    }
}