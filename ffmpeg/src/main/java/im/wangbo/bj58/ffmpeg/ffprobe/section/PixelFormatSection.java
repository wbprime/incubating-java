package im.wangbo.bj58.ffmpeg.ffprobe.section;

import java.util.StringJoiner;

/**
 * Sample data:
 *
 * "pixel_format": {
 *    "name": "yuv420p",
 *    "nb_components": 3,
 *    "log2_chroma_w": 1,
 *    "log2_chroma_h": 1,
 *    "bits_per_pixel": 12,
 *    "flags": {
 *      "big_endian": 0,
 *      "palette": 0,
 *      "bitstream": 0,
 *      "hwaccel": 0,
 *      "planar": 1,
 *      "rgb": 0,
 *      "pseudopal": 0,
 *      "alpha": 0
 *    },
 *    "components": [
 *      {
 *        "index": 1,
 *        "bit_depth": 8
 *      },
 *      {
 *        "index": 2,
 *        "bit_depth": 8
 *      },
 *      {
 *        "index": 3,
 *        "bit_depth": 8
 *      }
 *    ]
 *  }
 *
 * @author Elvis Wang
 */
public class PixelFormatSection {
    @Override
    public String toString() {
        return new StringJoiner(", ", PixelFormatSection.class.getSimpleName() + "[", "]")
                .toString();
    }
}
