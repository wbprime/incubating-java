package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
public interface ArgSpec {
    String name();

    String description();

    default boolean nameOnly() {
        return false;
    }

    default List<ValueValidator> validators() {
        return Collections.singletonList(ValueValidator.isTrue());
    }

    default Arg create(final String str) {
        if (nameOnly()) return SimpleArg.named(name());

        final List<String> errors = Lists.newArrayList();
        for (ValueValidator validator : validators()) {
            if (validator.validate(str)) {
                errors.add("discarded by " + validator);
            }
        }

        if (!errors.isEmpty()) {
            final StringBuilder sb = new StringBuilder("Value \"")
                .append(str)
                .append("\" for Arg \"")
                .append(name())
                .append("\" is invalid");

            for (String error : errors) {
                sb.append(" AND ").append(error);
            }

            throw new IllegalArgumentException(sb.toString());
        }

        return SimpleArg.paired(name(), str);
    }
}
