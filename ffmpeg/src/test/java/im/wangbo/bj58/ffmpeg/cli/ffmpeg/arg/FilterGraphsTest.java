package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.Filter;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.FilterArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.FilterChain;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.FilterGraph;
import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-21, by Elvis Wang
 */
class FilterGraphsTest {
    @ParameterizedTest
    @MethodSource("filterArgs")
    void filter(final Filter filter, final String expected) {
        final String result = FilterGraphs.describe(filter);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> filterArgs() {
        return Stream.of(
            Arguments.of(Filter.of("1paired1namedArg_1In_noOut",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.named("arg0")),
                Lists.immutable.of("in_1"),
                Lists.immutable.of("out_1")),
                "[in_1]1paired1namedArg_1In_noOut=arg=v:arg0[out_1]"),
            Arguments.of(Filter.of("1paired1namedArg_2In_2Out",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.named("arg0")),
                Lists.immutable.of("in_1", "in_2"),
                Lists.immutable.of("out_1", "out_2")),
                "[in_1][in_2]1paired1namedArg_2In_2Out=arg=v:arg0[out_1][out_2]"),
            Arguments.of(Filter.of("1paired1namedArg_noIn_1Out",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.named("arg0")),
                Lists.immutable.empty(),
                Lists.immutable.of("out_1")),
                "1paired1namedArg_noIn_1Out=arg=v:arg0[out_1]"),
            Arguments.of(Filter.of("1paired1namedArg_noIn_2Out",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.named("arg0")),
                Lists.immutable.empty(),
                Lists.immutable.of("out_1", "out_2")),
                "1paired1namedArg_noIn_2Out=arg=v:arg0[out_1][out_2]"),
            Arguments.of(Filter.of("1paired1namedArg_1In_noOut",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.named("arg0")),
                Lists.immutable.of("in_1"),
                Lists.immutable.empty()),
                "[in_1]1paired1namedArg_1In_noOut=arg=v:arg0"),
            Arguments.of(Filter.of("1paired1namedArg_2In_noOut",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.named("arg0")),
                Lists.immutable.of("in_1", "in_2"),
                Lists.immutable.empty()),
                "[in_1][in_2]1paired1namedArg_2In_noOut=arg=v:arg0"),
            Arguments.of(Filter.of("2namedArg_noIn_noOut",
                Lists.immutable.of(FilterArg.named("arg"), FilterArg.named("arg0")),
                Lists.immutable.empty(),
                Lists.immutable.empty()),
                "2namedArg_noIn_noOut=arg:arg0"),
            Arguments.of(Filter.of("2pairedArg_noIn_noOut",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.paired("arg0", "v0")),
                Lists.immutable.empty(),
                Lists.immutable.empty()),
                "2pairedArg_noIn_noOut=arg=v:arg0=v0"),
            Arguments.of(Filter.of("1named1pairedArg_noIn_noOut",
                Lists.immutable.of(FilterArg.named("arg"), FilterArg.paired("arg0", "v0")),
                Lists.immutable.empty(),
                Lists.immutable.empty()),
                "1named1pairedArg_noIn_noOut=arg:arg0=v0"),
            Arguments.of(Filter.of("1paired1namedArg_noIn_noOut",
                Lists.immutable.of(FilterArg.paired("arg", "v"), FilterArg.named("arg0")),
                Lists.immutable.empty(),
                Lists.immutable.empty()),
                "1paired1namedArg_noIn_noOut=arg=v:arg0"),
            Arguments.of(Filter.of("1namedArg_noIn_noOut",
                Lists.immutable.of(FilterArg.named("arg")),
                Lists.immutable.empty(),
                Lists.immutable.empty()),
                "1namedArg_noIn_noOut=arg"),
            Arguments.of(Filter.of("1pairedArg_noIn_noOut",
                Lists.immutable.of(FilterArg.paired("arg", "v")),
                Lists.immutable.empty(),
                Lists.immutable.empty()),
                "1pairedArg_noIn_noOut=arg=v"),
            Arguments.of(Filter.of("noArgs_noIn_noOut",
                Lists.immutable.empty(),
                Lists.immutable.empty(),
                Lists.immutable.empty()),
                "noArgs_noIn_noOut")
        );
    }

    @ParameterizedTest
    @MethodSource("chainArgs")
    void chain(final FilterChain chain, final String expected) {
        final String result = FilterGraphs.describe(chain);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    static FilterChain createChain(final String prefix, final int cnt) {
        MutableList<Filter> filters = Lists.mutable.withInitialCapacity(cnt);
        for (int i = 0; i < cnt; i++) {
            filters.add(Filter.of(prefix + i, Lists.immutable.empty(), Lists.immutable.empty(), Lists.immutable.empty()));
        }
        return FilterChain.of(filters.toImmutable());
    }

    static Stream<Arguments> chainArgs() {
        return Stream.of(
            Arguments.of(createChain("pre", 3),
                "pre0,pre1,pre2"),
            Arguments.of(createChain("prefix", 2),
                "prefix0,prefix1"),
            Arguments.of(createChain("prefix", 1),
                "prefix0"),
            Arguments.of(createChain("prefix", 0),
                "")
        );
    }

    @ParameterizedTest
    @MethodSource("graphArgs")
    void graph(final FilterGraph graph, final String expected) {
        final String result = FilterGraphs.describe(graph);

        Assertions.assertThat(result).isEqualTo(expected);
    }

    static FilterGraph createGraph(final String prefix, final int cnt) {
        MutableList<FilterChain> chains = Lists.mutable.withInitialCapacity(cnt);
        for (int i = 0; i < cnt; i++) {
            chains.add(createChain(prefix + i, cnt));
        }
        return FilterGraph.of(chains.toImmutable());
    }

    static Stream<Arguments> graphArgs() {
        return Stream.of(
            Arguments.of(createGraph("pre", 3),
                "pre00,pre01,pre02;pre10,pre11,pre12;pre20,pre21,pre22"),
            Arguments.of(createGraph("prefix", 2),
                "prefix00,prefix01;prefix10,prefix11"),
            Arguments.of(createGraph("prefix", 1),
                "prefix00"),
            Arguments.of(createGraph("prefix", 0),
                "")
        );
    }
}
