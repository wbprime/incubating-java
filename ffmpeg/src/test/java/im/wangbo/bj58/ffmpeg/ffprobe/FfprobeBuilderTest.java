package im.wangbo.bj58.ffmpeg.ffprobe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import im.wangbo.bj58.ffmpeg.arg.common.LogLevelArg;
import im.wangbo.bj58.ffmpeg.executor.StdExecutor;
import im.wangbo.bj58.ffmpeg.ffprobe.section.SectionSpecifier;
import im.wangbo.bj58.ffmpeg.ffprobe.writer.WriterFormat;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Disabled
class FfprobeBuilderTest {
    private ScheduledExecutorService scheduledExecutorService;

    @BeforeEach
    void setUp() {
        scheduledExecutorService = Executors.newScheduledThreadPool(4);
    }

    @AfterEach
    void tearDown() {
        scheduledExecutorService.shutdown();
    }

    @Test
    void buildAndRun() throws Exception {
        final FfprobeBuilder builder = FfprobeBuilder.builder("ffprobe");

        builder.hideBanner().logLevel(LogLevelArg.error())
                .addSectionSpecifier(SectionSpecifier.FORMAT)
                .addSectionSpecifier(SectionSpecifier.STREAMS)
                .writerFormat(WriterFormat.json())
                .buildAndExecute(
                        URI.create("http://prod1.wos.58dns.org/IjGfEdCbIlr/ishare/course_ware_1560506548694.flv"),
                        StdExecutor.create(scheduledExecutorService)
                )
                .thenAccept(System.out::println)
                .toCompletableFuture().get();
    }
}