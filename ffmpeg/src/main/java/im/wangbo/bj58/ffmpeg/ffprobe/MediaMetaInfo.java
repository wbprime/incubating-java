package im.wangbo.bj58.ffmpeg.ffprobe;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.json.bind.annotation.JsonbProperty;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class MediaMetaInfo {
    @Nullable
    @JsonbProperty("streams")
    private List<StreamInfo> streams;

    @Nullable
    @JsonbProperty("format")
    private FormatInfo format;

    @Nullable
    @JsonbProperty("frames")
    private List<FrameInfo> frames;

    @Nullable
    @JsonbProperty("packets")
    private List<PacketInfo> packets;

    @Nullable
    public List<StreamInfo> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamInfo> streams) {
        this.streams = streams;
    }

    @Nullable
    public FormatInfo getFormat() {
        return format;
    }

    public void setFormat(FormatInfo format) {
        this.format = format;
    }

    public static class FormatInfo {
        /*
         * "format": {
         *     "filename": "out.mp4",
         *     "nb_streams": 2,
         *     "nb_programs": 0,
         *     "format_name": "mov,mp4,m4a,3gp,3g2,mj2",
         *     "format_long_name": "QuickTime / MOV",
         *     "start_time": "0.000000 s",
         *     "duration": "65.000000 s",
         *     "size": "10086956 byte",
         *     "bit_rate": "1241471 bit/s",
         *     "probe_score": 100,
         *     "tags": {
         *       "major_brand": "isom",
         *       "minor_version": "512",
         *       "compatible_brands": "isomiso2avc1mp41",
         *       "encoder": "Lavf57.71.100"
         *     }
         *  }
         */

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FormatInfo that = (FormatInfo) o;
            return Objects.equals(duration, that.duration) &&
                    Objects.equals(formatName, that.formatName) &&
                    Objects.equals(fileSize, that.fileSize) &&
                    Objects.equals(bitRate, that.bitRate) &&
                    Objects.equals(startTime, that.startTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(duration, formatName, fileSize, bitRate, startTime);
        }

        @Override
        public String toString() {
            return "FormatInfo{" +
                    "duration='" + duration + '\'' +
                    ", formatName='" + formatName + '\'' +
                    ", fileSize='" + fileSize + '\'' +
                    ", bitRate='" + bitRate + '\'' +
                    ", startTime='" + startTime + '\'' +
                    '}';
        }
    }

    public static class StreamInfo {
        /*
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
         */

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
            StreamInfo that = (StreamInfo) o;
            return Objects.equals(width, that.width) &&
                    Objects.equals(height, that.height) &&
                    Objects.equals(codecName, that.codecName) &&
                    Objects.equals(codecType, that.codecType) &&
                    Objects.equals(duration, that.duration) &&
                    Objects.equals(bitRate, that.bitRate) &&
                    Objects.equals(frameRate, that.frameRate) &&
                    Objects.equals(sampleRate, that.sampleRate) &&
                    Objects.equals(tags, that.tags);
        }

        @Override
        public int hashCode() {

            return Objects.hash(codecName, codecType, width, height, duration, bitRate, frameRate, sampleRate, tags);
        }

        @Override
        public String toString() {
            return "StreamInfo{" +
                    "codecName='" + codecName + '\'' +
                    ", codecType='" + codecType + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", duration='" + duration + '\'' +
                    ", bitRate='" + bitRate + '\'' +
                    ", frameRate='" + frameRate + '\'' +
                    ", sampleRate='" + sampleRate + '\'' +
                    ", tags=" + tags +
                    '}';
        }
    }

    public static class FrameInfo {
        /*
         *   {
         *      "media_type": "video",
         *      "stream_index": 0,
         *      "key_frame": 0,
         *      "pkt_pts": 137216,
         *      "pkt_pts_time": "10.720000 s",
         *      "pkt_dts": 137216,
         *      "pkt_dts_time": "10.720000 s",
         *      "best_effort_timestamp": 137216,
         *      "best_effort_timestamp_time": "10.720000 s",
         *      "pkt_duration": 512,
         *      "pkt_duration_time": "0.040000 s",
         *      "pkt_pos": "5180421",
         *      "pkt_size": "24814 byte",
         *      "width": 1280,
         *      "height": 720,
         *      "pix_fmt": "yuv420p",
         *      "sample_aspect_ratio": "1:1",
         *      "pict_type": "P",
         *      "coded_picture_number": 268,
         *      "display_picture_number": 0,
         *      "interlaced_frame": 0,
         *      "top_field_first": 0,
         *      "repeat_pict": 0,
         *      "chroma_location": "left"
         *  }
         */

        @Override
        public String toString() {
            return "FrameInfo{}";
        }
    }

    public static class PacketInfo {
        /*
         * {
         *  "codec_type": "video",
         *  "stream_index": 0,
         *  "pts": 0,
         *  "pts_time": "0.000000 s",
         *  "dts": 0,
         *  "dts_time": "0.000000 s",
         *  "duration": 512,
         *  "duration_time": "0.040000 s",
         *  "size": "146067 byte",
         *  "pos": "184002",
         *  "flags": "K_"
         * }
         */

        @Override
        public String toString() {
            return "PacketInfo{}";
        }
    }

    @Override
    public String toString() {
        return "MediaMetaInfo{" +
                "streams=" + streams +
                ", format=" + format +
                ", frames=" + frames +
                ", packets=" + packets +
                '}';
    }
}
