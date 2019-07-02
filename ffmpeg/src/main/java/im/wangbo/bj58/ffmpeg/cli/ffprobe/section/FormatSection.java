package im.wangbo.bj58.ffmpeg.cli.ffprobe.section;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.json.bind.annotation.JsonbProperty;

/**
 * Sample data:
 * <p>
 * "format": {
 * "filename": "out.mp4",
 * "nb_streams": 2,
 * "nb_programs": 0,
 * "format_name": "mov,mp4,m4a,3gp,3g2,mj2",
 * "format_long_name": "QuickTime / MOV",
 * "start_time": "0.000000 s",
 * "duration": "65.000000 s",
 * "size": "10086956 byte",
 * "bit_rate": "1241471 bit/s",
 * "probe_score": 100,
 * "tags": {
 * "major_brand": "isom",
 * "minor_version": "512",
 * "compatible_brands": "isomiso2avc1mp41",
 * "encoder": "Lavf57.71.100"
 * }
 * }
 *
 * @author Elvis Wang
 */
public class FormatSection {
    @JsonbProperty("duration")
    @Nullable
    private String duration;
    @JsonbProperty("format_name")
    @Nullable
    private String formatName;
    @JsonbProperty("size")
    @Nullable
    private String fileSize;
    @JsonbProperty("bit_rate")
    @Nullable
    private String bitRate;
    @JsonbProperty("start_time")
    @Nullable
    private String startTime;
    @JsonbProperty("tags")
    @Nullable
    private Map<String, String> tags;

    @Nullable
    public String getDuration() {
        return duration;
    }

    public void setDuration(@Nullable String duration) {
        this.duration = duration;
    }

    @Nullable
    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(@Nullable String formatName) {
        this.formatName = formatName;
    }

    @Nullable
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(@Nullable String size) {
        this.fileSize = size;
    }

    @Nullable
    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(@Nullable String bitRate) {
        this.bitRate = bitRate;
    }

    @Nullable
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(@Nullable String startTime) {
        this.startTime = startTime;
    }

    @Nullable
    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(@Nullable Map<String, String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormatSection that = (FormatSection) o;
        return Objects.equals(duration, that.duration) &&
                Objects.equals(formatName, that.formatName) &&
                Objects.equals(fileSize, that.fileSize) &&
                Objects.equals(bitRate, that.bitRate) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, formatName, fileSize, bitRate, startTime, tags);
    }

    @Override
    public String toString() {
        return "FormatSection{" +
                "duration='" + duration + '\'' +
                ", formatName='" + formatName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", bitRate='" + bitRate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", tags=" + tags +
                '}';
    }
}
