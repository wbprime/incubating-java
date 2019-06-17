package im.wangbo.bj58.ffmpeg.ffprobe.section;

/**
 * Sample data:
 *
 *  "format": {
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
 *
 * @author Elvis Wang
 */
public class FrameSection {
    @Override
    public String toString() {
        return "FrameSection{}";
    }
}
