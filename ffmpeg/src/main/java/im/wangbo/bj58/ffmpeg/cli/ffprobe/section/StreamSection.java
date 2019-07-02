package im.wangbo.bj58.ffmpeg.cli.ffprobe.section;

import javax.annotation.Nullable;
import javax.json.bind.annotation.JsonbProperty;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Sample data:
 *
 * "streams": [                                                                                                                               [83/46941]
 *    {
 *      "index": 0,
 *      "codec_name": "h264",
 *      "codec_long_name": "H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10",
 *      "profile": "High",
 *      "codec_type": "video",
 *      "codec_time_base": "1/48",
 *      "codec_tag_string": "avc1",
 *      "codec_tag": "0x31637661",
 *      "width": 544,
 *      "height": 960,
 *      "coded_width": 544,
 *      "coded_height": 960,
 *      "has_b_frames": 2,
 *      "sample_aspect_ratio": "0:1",
 *      "display_aspect_ratio": "0:1",
 *      "pix_fmt": "yuv420p",
 *      "level": 31,
 *      "chroma_location": "left",
 *      "refs": 1,
 *      "is_avc": "true",
 *      "nal_length_size": "4",
 *      "r_frame_rate": "24/1",
 *      "avg_frame_rate": "24/1",
 *      "time_base": "1/12288",
 *      "start_pts": 0,
 *      "start_time": "0.000000",
 *      "duration_ts": 798720,
 *      "duration": "65.000000",
 *      "bit_rate": "1107035"
 *    },
 *    {
 *      "index": 1,
 *      "codec_name": "aac",
 *      "codec_long_name": "AAC (Advanced Audio Coding)",
 *      "profile": "LC",
 *      "codec_type": "audio",
 *      "codec_time_base": "1/44100",
 *      "codec_tag_string": "mp4a",
 *      "codec_tag": "0x6134706d",
 *      "sample_fmt": "fltp",
 *      "sample_rate": "44100",
 *      "channels": 2,
 *      "channel_layout": "stereo",
 *      "bits_per_sample": 0,
 *      "r_frame_rate": "0/0",
 *      "avg_frame_rate": "0/0",
 *      "time_base": "1/44100",
 *      "start_pts": 0,
 *      "start_time": "0.000000",
 *      "duration_ts": 2860017,
 *      "duration": "64.852993",
 *      "bit_rate": "128724",
 *      "max_bit_rate": "128724",
 *      "nb_frames": "2794"
 *    }
 * ]
 *
 * @author Elvis Wang
 */
public class StreamSection {
    @JsonbProperty("index")
    @Nullable
    private Integer index;
    @JsonbProperty("codec_name")
    @Nullable
    private String codecName;
    @JsonbProperty("codec_type")
    @Nullable
    private String codecType;
    @JsonbProperty("width")
    @Nullable
    private Integer width;
    @JsonbProperty("height")
    @Nullable
    private Integer height;
    @JsonbProperty("duration")
    @Nullable
    private String duration;
    @JsonbProperty("bit_rate")
    @Nullable
    private String bitRate;
    @JsonbProperty("r_frame_rate")
    @Nullable
    private String frameRate;

    @JsonbProperty("sample_rate")
    @Nullable
    private String sampleRate;

    @JsonbProperty("tags")
    @Nullable
    private Map<String, String> tags;

    @Nullable
    public Integer getIndex() {
        return index;
    }

    public void setIndex(@Nullable Integer index) {
        this.index = index;
    }

    public String getCodecName() {
        return codecName;
    }

    public void setCodecName(String codecName) {
        this.codecName = codecName;
    }

    public String getCodecType() {
        return codecType;
    }

    public void setCodecType(String codecType) {
        this.codecType = codecType;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(String frameRate) {
        this.frameRate = frameRate;
    }

    public String getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(String sampleRate) {
        this.sampleRate = sampleRate;
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
        StreamSection that = (StreamSection) o;
        return Objects.equals(index, that.index) &&
                Objects.equals(codecName, that.codecName) &&
                Objects.equals(codecType, that.codecType) &&
                Objects.equals(width, that.width) &&
                Objects.equals(height, that.height) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(bitRate, that.bitRate) &&
                Objects.equals(frameRate, that.frameRate) &&
                Objects.equals(sampleRate, that.sampleRate) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, codecName, codecType, width, height, duration, bitRate, frameRate, sampleRate, tags);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StreamSection.class.getSimpleName() + "[", "]")
                .add("index=" + index)
                .add("codecName='" + codecName + "'")
                .add("codecType='" + codecType + "'")
                .add("width=" + width)
                .add("height=" + height)
                .add("duration='" + duration + "'")
                .add("bitRate='" + bitRate + "'")
                .add("frameRate='" + frameRate + "'")
                .add("sampleRate='" + sampleRate + "'")
                .add("tags=" + tags)
                .toString();
    }
}
