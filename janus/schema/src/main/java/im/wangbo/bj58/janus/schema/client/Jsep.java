package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class Jsep {
    public abstract String type();

    public abstract String sdp();

    public static Jsep offer(final String sdp) {
        return create("offer", sdp);
    }

    public static Jsep answer(final String sdp) {
        return create("answer", sdp);
    }

    private static Jsep create(final String type, final String sdp) {
        return new AutoValue_Jsep(type, sdp);
    }
}
