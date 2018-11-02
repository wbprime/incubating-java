package im.wangbo.bj58.incubating.wtable.core;

import com.google.common.base.Charsets;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class Wtables {
    private Wtables() { throw new AssertionError("Construction forbidden"); }

    private static final class BytesRowKey implements RowKey {
        private final byte[] inner;

        BytesRowKey(byte[] inner) {
            this.inner = checkNotNull(inner, "rowKey bytes should not be null but was");
        }

        @Override
        public byte[] get() {
            return inner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BytesRowKey that = (BytesRowKey) o;
            return Arrays.equals(inner, that.inner);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(inner);
        }

        @Override
        public String toString() {
            return "BytesRowKey{" +
                    "base64=" + Base64.getEncoder().encodeToString(inner) +
                    '}';
        }
    }

    private static final class BytesColKey implements ColKey {
        private final byte[] inner;

        BytesColKey(byte[] inner) {
            this.inner = checkNotNull(inner, "colKey bytes should not be null but was");
        }

        @Override
        public byte[] get() {
            return inner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BytesRowKey that = (BytesRowKey) o;
            return Arrays.equals(inner, that.inner);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(inner);
        }

        @Override
        public String toString() {
            return "BytesColKey{" +
                    "base64=" + Base64.getEncoder().encodeToString(inner) +
                    '}';
        }
    }

    private static abstract class AbstractValue implements Value {
        private final long score;
        private final Duration ttl;

        AbstractValue(long score, Duration ttl) {
            this.score = score;
            this.ttl = checkNotNull(ttl, "ttl duration should not be null but was");
        }

        @Override
        public long score() {
            return score;
        }

        @Override
        public Duration ttl() {
            return ttl;
        }
    }

    private static final class BytesValue extends AbstractValue {
        private final byte[] inner;

        BytesValue(byte[] inner, long score, Duration ttl) {
            super(score, ttl);
            this.inner = checkNotNull(inner, "value bytes should not be null but was");
        }

        @Override
        public byte[] get() {
            return inner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BytesValue that = (BytesValue) o;
            return Arrays.equals(inner, that.inner)
                    && Objects.equals(score(), that.score()) && Objects.equals(ttl(), that.ttl());
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(score(), ttl());
            result = 31 * result + Arrays.hashCode(inner);
            return result;
        }

        @Override
        public String toString() {
            return "BytesValue{" +
                    "base64=" + Base64.getEncoder().encodeToString(inner) +
                    ", score=" + score() +
                    ", ttl=" + ttl() +
                    '}';
        }
    }

    private static final class StringRowKey implements RowKey {
        private final String str;
        private final Charset charset;

        StringRowKey(String str, Charset charset) {
            this.str = checkNotNull(str, "rowKey string should not be null but was");
            this.charset = checkNotNull(charset, "rowKey charset should not be null but was");
        }

        @Override
        public byte[] get() {
            return str.getBytes(charset);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StringRowKey that = (StringRowKey) o;
            return Objects.equals(str, that.str) &&
                    Objects.equals(charset, that.charset);
        }

        @Override
        public int hashCode() {

            return Objects.hash(str, charset);
        }

        @Override
        public String toString() {
            return "StringRowKey{" +
                    "str='" + str + '\'' +
                    ", charset=" + charset +
                    '}';
        }
    }

    private static final class StringColKey implements ColKey {
        private final String str;
        private final Charset charset;

        StringColKey(String str, Charset charset) {
            this.str = checkNotNull(str, "colKey string should not be null but was");
            this.charset = checkNotNull(charset, "colKey charset should not be null but was");
        }

        @Override
        public byte[] get() {
            return str.getBytes(charset);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StringRowKey that = (StringRowKey) o;
            return Objects.equals(str, that.str) &&
                    Objects.equals(charset, that.charset);
        }

        @Override
        public int hashCode() {

            return Objects.hash(str, charset);
        }

        @Override
        public String toString() {
            return "StringColKey{" +
                    "str='" + str + '\'' +
                    ", charset=" + charset +
                    '}';
        }
    }

    private static final class StringValue extends AbstractValue {
        private final String str;
        private final Charset charset;

        StringValue(String str, Charset charset, long score, Duration ttl) {
            super(score, ttl);
            this.str = checkNotNull(str, "value string should not be null but was");
            this.charset = checkNotNull(charset, "value string charset should not be null but was");
        }

        @Override
        public byte[] get() {
            return str.getBytes(charset);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StringValue that = (StringValue) o;
            return Objects.equals(str, that.str) &&
                    Objects.equals(charset, that.charset)
                    && Objects.equals(score(), that.score()) && Objects.equals(ttl(), that.ttl());
        }

        @Override
        public int hashCode() {

            return Objects.hash(str, charset, score(), ttl());
        }

        @Override
        public String toString() {
            return "StringValue{" +
                    "score=" + score() +
                    ", ttl=" + ttl() +
                    ", str='" + str + '\'' +
                    ", charset=" + charset +
                    '}';
        }
    }

    public static RowKey rowKey(final byte[] inner) {
        return new BytesRowKey(inner);
    }

    public static RowKey rowKey(final String str) {
        return new StringRowKey(str, Charsets.UTF_8);
    }

    public static RowKey rowKey(final String str, final Charset charset) {
        return new StringRowKey(str, charset);
    }

    public static ColKey colKey(final byte[] inner) {
        return new BytesColKey(inner);
    }

    public static ColKey colKey(final String str) {
        return new StringColKey(str, Charsets.UTF_8);
    }

    public static ColKey colKey(final String str, final Charset charset) {
        return new StringColKey(str, charset);
    }

    public static Value value(final byte[] inner) {
        return value(inner, 0L, Duration.ofSeconds(0));
    }

    public static Value value(final byte[] inner, final long score) {
        return value(inner, score, Duration.ofSeconds(0));
    }

    public static Value value(final byte[] inner, final Duration ttl) {
        return value(inner, 0L, ttl);
    }

    public static Value value(final byte[] inner, final long score, final Duration ttl) {
        return new BytesValue(inner, score, ttl);
    }

    public static Value value(final String str, final Charset charset) {
        return value(str, charset, 0L, Duration.ofSeconds(0));
    }

    public static Value value(final String str) {
        return value(str, Charsets.UTF_8, 0L, Duration.ofSeconds(0));
    }

    public static Value value(final String str, final Charset charset, final long score) {
        return value(str, charset, score, Duration.ofSeconds(0));
    }

    public static Value value(final String str, final long score) {
        return value(str, Charsets.UTF_8, score, Duration.ofSeconds(0));
    }

    public static Value value(final String str, final Charset charset, final Duration ttl) {
        return value(str, charset, 0L, ttl);
    }

    public static Value value(final String str, final Duration ttl) {
        return value(str, Charsets.UTF_8, 0L, ttl);
    }

    public static Value value(final String str, final Charset charset, final long score, final Duration ttl) {
        return new StringValue(str, charset, score, ttl);
    }

    public static Value value(final String str, final long score, final Duration ttl) {
        return new StringValue(str, Charsets.UTF_8, score, ttl);
    }
}
