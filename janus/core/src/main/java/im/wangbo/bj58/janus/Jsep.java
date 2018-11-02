package im.wangbo.bj58.janus;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
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
