package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import im.wangbo.bj58.ffmpeg.cli.ffprobe.section.*;

import javax.annotation.Nullable;
import javax.json.bind.annotation.JsonbProperty;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * TODO add brief description here
 * <p>
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class MediaMetaInfo {
    @Nullable
    @JsonbProperty("streams")
    private List<StreamSection> streams;

    @Nullable
    @JsonbProperty("format")
    private FormatSection format;

    @Nullable
    @JsonbProperty("frames")
    private List<FrameSection> frames;

    @Nullable
    @JsonbProperty("packets")
    private List<PacketSection> packets;

    @Nullable
    @JsonbProperty("programs")
    private List<ProgramSection> programs;

    @Nullable
    @JsonbProperty("chapters")
    private List<ChapterSection> chapters;

    @Nullable
    @JsonbProperty("pixel_formats")
    private List<PixelFormatSection> pixelFormats;

    @Nullable
    @JsonbProperty("error")
    private ErrorSection error;

    @Nullable
    @JsonbProperty("program_version")
    private ProgramVersionSection programVersion;

    @Nullable
    @JsonbProperty("library_versions")
    private List<LibraryVersionSection> libraryVersions;

    @Nullable
    public List<StreamSection> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamSection> streams) {
        this.streams = streams;
    }

    @Nullable
    public FormatSection getFormat() {
        return format;
    }

    public void setFormat(FormatSection format) {
        this.format = format;
    }

    @Nullable
    public List<FrameSection> getFrames() {
        return frames;
    }

    public void setFrames(@Nullable List<FrameSection> frames) {
        this.frames = frames;
    }

    @Nullable
    public List<PacketSection> getPackets() {
        return packets;
    }

    public void setPackets(@Nullable List<PacketSection> packets) {
        this.packets = packets;
    }

    @Nullable
    public List<ProgramSection> getPrograms() {
        return programs;
    }

    public void setPrograms(@Nullable List<ProgramSection> programs) {
        this.programs = programs;
    }

    @Nullable
    public List<ChapterSection> getChapters() {
        return chapters;
    }

    public void setChapters(@Nullable List<ChapterSection> chapters) {
        this.chapters = chapters;
    }

    @Nullable
    public List<PixelFormatSection> getPixelFormats() {
        return pixelFormats;
    }

    public void setPixelFormats(@Nullable List<PixelFormatSection> pixelFormats) {
        this.pixelFormats = pixelFormats;
    }

    @Nullable
    public ErrorSection getError() {
        return error;
    }

    public void setError(@Nullable ErrorSection error) {
        this.error = error;
    }

    @Nullable
    public ProgramVersionSection getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(@Nullable ProgramVersionSection programVersion) {
        this.programVersion = programVersion;
    }

    @Nullable
    public List<LibraryVersionSection> getLibraryVersions() {
        return libraryVersions;
    }

    public void setLibraryVersions(@Nullable List<LibraryVersionSection> libraryVersions) {
        this.libraryVersions = libraryVersions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaMetaInfo that = (MediaMetaInfo) o;
        return Objects.equals(streams, that.streams) &&
                Objects.equals(format, that.format) &&
                Objects.equals(frames, that.frames) &&
                Objects.equals(packets, that.packets) &&
                Objects.equals(programs, that.programs) &&
                Objects.equals(chapters, that.chapters) &&
                Objects.equals(pixelFormats, that.pixelFormats) &&
                Objects.equals(error, that.error) &&
                Objects.equals(programVersion, that.programVersion) &&
                Objects.equals(libraryVersions, that.libraryVersions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streams, format, frames, packets, programs, chapters, pixelFormats, error, programVersion, libraryVersions);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MediaMetaInfo.class.getSimpleName() + "[", "]")
                .add("streams=" + streams)
                .add("format=" + format)
                .add("frames=" + frames)
                .add("packets=" + packets)
                .add("programs=" + programs)
                .add("chapters=" + chapters)
                .add("pixelFormats=" + pixelFormats)
                .add("error=" + error)
                .add("programVersion=" + programVersion)
                .add("libraryVersions=" + libraryVersions)
                .toString();
    }
}
