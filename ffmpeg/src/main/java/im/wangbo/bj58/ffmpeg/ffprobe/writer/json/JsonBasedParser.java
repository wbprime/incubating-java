package im.wangbo.bj58.ffmpeg.ffprobe.writer.json;

import javax.json.bind.JsonbBuilder;

import im.wangbo.bj58.ffmpeg.ffprobe.MediaMetaInfo;
import im.wangbo.bj58.ffmpeg.ffprobe.writer.Parser;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class JsonBasedParser implements Parser {
    @Override
    public MediaMetaInfo parse(final String str) {
        return JsonbBuilder.create().fromJson(str, MediaMetaInfo.class);
    }
}
