package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json;

import im.wangbo.bj58.ffmpeg.cli.ffprobe.MediaMetaInfo;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.Parser;

import javax.json.bind.JsonbBuilder;

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
