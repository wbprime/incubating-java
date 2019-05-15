package im.wangbo.bj58.ffmpeg.impl;

import com.google.auto.value.AutoValue;

import java.util.List;

import im.wangbo.bj58.ffmpeg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class StdArg implements Arg {
    abstract List<String> opts();
}
