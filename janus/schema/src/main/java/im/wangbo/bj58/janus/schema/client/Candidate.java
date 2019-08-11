package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 * <p>
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class Candidate {
    public abstract String sdpMid();

    public abstract Integer sdpMlineIndex();

    public abstract String candidate();

    public static Candidate video(final int sdpMlineIndex, final String candidate) {
        return create("video", sdpMlineIndex, candidate);
    }

    public static Candidate audio(final int sdpMlineIndex, final String candidate) {
        return create("audio", sdpMlineIndex, candidate);
    }

    private static Candidate create(
        final String sdpMid, final Integer sdpMlineIndex, final String candidate
    ) {
        return new AutoValue_Candidate(sdpMid, sdpMlineIndex, candidate);
    }
}
